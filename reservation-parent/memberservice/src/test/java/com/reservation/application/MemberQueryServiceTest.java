package com.reservation.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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

import com.reservation.factory.MemberFactory;
import com.reservation.memberservice.application.MemberQueryServiceImpl;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.error.InvalidUserIdException;

/**
 * MemberQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberQueryServiceTest {
	public final static String USER_ID = MemberFactory.USER_ID;
	public final static String PHONE_NUM = MemberFactory.PHONE_NUM;
	public final static String USERNAME = MemberFactory.USERNAME;
	public final static String ADDRESS = MemberFactory.ADDRESS;
	public final static String PASSWORD = MemberFactory.PASSWORD;

	@InjectMocks
	private MemberQueryServiceImpl memberQueryService;

	@Mock
	private MemberRepository memberRepository;

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
		@DisplayName("유효하지 않은 ID 조회로 예외 발생")
		void invalidIDLookupExceptionOccurred() {
			when(memberRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
			assertThatThrownBy(() -> memberQueryService.selectPerformancesByUserId(USER_ID))
				.isInstanceOf(InvalidUserIdException.class);
		}

		@ParameterizedTest
		@MethodSource("emptyUserId")
		@DisplayName("잘못된 UserID 값, 예외 발생")
		void invalidUserIdValueTest(String userId) {
			assertThatThrownBy(() -> memberQueryService.selectPerformancesByUserId(userId))
				.isInstanceOf(IllegalArgumentException.class);
		}

		static Stream<Arguments> emptyUserId() {
			return Stream.of(
				Arguments.of(""),
				Arguments.of((Object)null)
			);
		}
	}

}
