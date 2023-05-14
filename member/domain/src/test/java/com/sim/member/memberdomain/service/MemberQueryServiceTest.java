package com.sim.member.memberdomain.service;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.sim.member.clients.performanceclient.client.PerformanceClient;
import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.factory.MemberFactory;
import com.sim.member.memberdomain.factory.PerformanceFactory;
import com.sim.member.memberdomain.repository.MemberRepository;

import dto.Performance;

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
	private final static String USERNAME = MemberFactory.USERNAME;
	private final static String ADDRESS = MemberFactory.ADDRESS;

	@InjectMocks
	private MemberQueryServiceImpl memberQueryService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PerformanceClient performanceClient;

	@Nested
	@DisplayName("회원 ID로 회원 상세 조회")
	class ViewMemberDetailsByMemberIdTest {
		@Test
		@DisplayName("회원 조회 성공, 값 일치 확인")
		void memberInquirySuccessTest() {
			//given
			Member member = createMember();
			given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.of(member));

			//when
			MemberDto memberDto = memberQueryService.findMemberByUserId(member.getUserId());

			//then
			assertThat(memberDto.getUserId()).isEqualTo(USER_ID);
			assertThat(memberDto.getAddress()).isEqualTo(ADDRESS);
			assertThat(memberDto.getUsername()).isEqualTo(USERNAME);
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
			List<Performance> performances = createPerformanceList();

			when(performanceClient.getPerformanceByMemberId(MEMBER_ID)).thenReturn(performances);
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

	}

	private Member createMember(String userId, String username) {
		return MemberFactory.createMember(userId, username);
	}

	private Member createMember() {
		return MemberFactory.createMember();
	}

	private List<Performance> createPerformanceList() {
		return PerformanceFactory.createPerformanceList();
	}
}
