package com.reservation.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.common.dto.PerformanceDto;
import com.reservation.factory.MemberFactory;
import com.reservation.factory.PerformanceDtoFactory;
import com.reservation.memberservice.application.MemberQueryServiceImpl;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.client.PerformanceApiClient;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;
import com.reservation.memberservice.error.InvalidUserIdException;
import com.reservation.memberservice.error.MemberNotFoundException;

/**
 * MemberQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberQueryServiceTest {
	private final static Long MEMBER_ID = MemberFactory.MEMBER_ID;
	private final static String USER_ID = MemberFactory.USER_ID;
	private final static String PHONE_NUM = MemberFactory.PHONE_NUM;
	private final static String USERNAME = MemberFactory.USERNAME;
	private final static String ADDRESS = MemberFactory.ADDRESS;
	private final static String PASSWORD = MemberFactory.PASSWORD;

	@InjectMocks
	private MemberQueryServiceImpl memberQueryService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PerformanceApiClient performanceApiClient;

	@Spy
	MemberInfoDtoMapper memberInfoDtoMapper = MemberInfoDtoMapper.INSTANCE;

	@Nested
	@DisplayName("회원 ID로 회원 상세 조회")
	class ViewMemberDetailsByMemberIdTest {
		@Test
		@DisplayName("회원 조회 성공, 값 일치 확인")
		void memberInquirySuccessTest() {
			//given
			Member member = MemberFactory.createMember();
			given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.of(member));

			//when
			MemberInfoDto findMemberDto = memberQueryService.findMemberByUserId(member.getUserId());

			//then
			assertThat(findMemberDto.getUserId()).isEqualTo(USER_ID);
			assertThat(findMemberDto.getAddress()).isEqualTo(ADDRESS);
			assertThat(findMemberDto.getUsername()).isEqualTo(USERNAME);
		}

		@Test
		@DisplayName("유효하지 않은 회원 ID, 예외 발생")
		void noMembersMatchingException() {
			given(memberRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));
			assertThatThrownBy(() -> memberQueryService.findMemberByUserId(USER_ID))
				.isInstanceOf(InvalidUserIdException.class);
		}

		@ParameterizedTest
		@MethodSource("emptyUserId")
		@DisplayName("잘못된 UserID 값, 예외 발생")
		void invalidUserIdValueTest(String userId) {
			assertThatThrownBy(() -> memberQueryService.findMemberByUserId(userId))
				.isInstanceOf(IllegalArgumentException.class);
		}

		static Stream<Arguments> emptyUserId() {
			return Stream.of(
				Arguments.of(""),
				Arguments.of((Object)null)
			);
		}
	}

	@Nested
	@DisplayName("회원 ID로 공연 조회")
	class PerformanceSearchByMemberIdTest {

		@Test
		@DisplayName("회원 ID로 공연 조회 성공")
		void SuccessPerformanceSearchByMemberId() {
			// given
			Member member = createMember(USER_ID, USERNAME);
			when(performanceApiClient.getPerformanceByMemberId(MEMBER_ID)).thenReturn(createPerformanceDtoList());
			when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));

			// when
			MemberPerformanceDto memberPerformanceDto = memberQueryService.selectPerformancesById(MEMBER_ID);

			// then
			assertThat(memberPerformanceDto.getPerformances()).isNotEmpty();
			assertThat(memberPerformanceDto.getUserId()).isEqualTo(USER_ID);
			assertThat(memberPerformanceDto.getUserName()).isEqualTo(USERNAME);
		}

		@Test
		@DisplayName("유효하지 않은 ID 조회로 예외 발생")
		void invalidIDLookupExceptionOccurred() {
			when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.empty());
			assertThatThrownBy(() -> memberQueryService.selectPerformancesById(MEMBER_ID))
				.isInstanceOf(MemberNotFoundException.class);
		}

		@Test
		@DisplayName("잘못된 UserID 값, 예외 발생")
		void invalidUserIdValueTest() {
			assertThatThrownBy(() -> memberQueryService.selectPerformancesById(null))
				.isInstanceOf(IllegalArgumentException.class);
		}

		private List<PerformanceDto> createPerformanceDtoList() {
			return PerformanceDtoFactory.createPerformanceDtoList();
		}

		private Member createMember(String userId, String username) {
			return MemberFactory.createMember(userId, username);
		}
	}

}
