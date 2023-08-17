package com.sim.member.memberdomain.service;

import com.sim.member.clients.performanceclient.client.PerformanceClient;
import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.repository.MemberRepository;
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

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

/**
 * MemberQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberQueryServiceUnitTest {
	private final static Long MEMBER_ID = 1L;
	private final static String USER_ID = "test";
	private final static String USERNAME = "이순신";
	private final static String ADDRESS = "서울시 마포구 창천동";
	private final static String PHONE_NUM = "010-1111-9999";
	private final static String PASSWORD = "password";

	@InjectMocks
	private MemberQueryServiceImpl memberQueryService;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PerformanceClient performanceClient;

	@Nested
	@DisplayName("회원 정보 조회")
	class ViewMemberDetailsByMemberIdTest {
		@Test
		@DisplayName("회원 ID로 회원 정보 조회가 가능하다.")
		void memberInquirySuccessTest() {
			//given
			Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
			given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.of(member));

			//when
			MemberDto memberDto = memberQueryService.findMemberByUserId(USER_ID);

			//then
			assertThat(memberDto.getUserId()).isEqualTo(USER_ID);
			assertThat(memberDto.getAddress()).isEqualTo(ADDRESS);
			assertThat(memberDto.getUsername()).isEqualTo(USERNAME);
		}

		@Test
		@DisplayName("등록되지 않은 회원 조회시 예외가 발생한다.")
		void noMembersMatchingException() {
			// given
			given(memberRepository.findByUserId(any())).willReturn(Optional.empty());

			// when, then
			assertThatThrownBy(() -> memberQueryService.findMemberByUserId(USER_ID))
				.isInstanceOf(InvalidUserIdException.class);
		}

		@ParameterizedTest
		@MethodSource("emptyUserId")
		@DisplayName("잘못된 회원 ID 값으로 조회시 예외가 발생한다.")
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
		@DisplayName("회원 ID로 회원 정보 조회가 가능하다.")
		void SuccessPerformanceSearchByMemberId() {
			// given
			given(performanceClient.getPerformanceByMemberId(MEMBER_ID)).willReturn(Collections.emptyList());

			Member member = createMember(USER_ID, USERNAME);
			given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(member));

			// when
			MemberPerformanceDto memberPerformanceDto = memberQueryService.selectPerformancesById(MEMBER_ID);

			// then
			assertThat(memberPerformanceDto.getUserId()).isEqualTo(USER_ID);
			assertThat(memberPerformanceDto.getUserName()).isEqualTo(USERNAME);
			verify(memberRepository).findById(MEMBER_ID);
		}

		@Test
		@DisplayName("등록되지 않은 Member ID로 조회시 예외가 발생한다.")
		void invalidIDLookupExceptionOccurred() {
			// given
			given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.empty());

			// when
			assertThatThrownBy(() -> memberQueryService.selectPerformancesById(MEMBER_ID))
				.isInstanceOf(MemberNotFoundException.class);
		}

		@Test
		@DisplayName("Member ID가 null 일 시 예외가 발생한다.")
		void invalidUserIdValueTest() {
			assertThatThrownBy(() -> memberQueryService.selectPerformancesById(null))
				.isInstanceOf(IllegalArgumentException.class);
		}

	}

	private Member createMember(String userId, String username) {
		return Member.of(userId, username, PASSWORD, PHONE_NUM, ADDRESS);
	}

	private Member createMember(String userId, String username, String password, String phoneNum, String address) {
		return Member.of(userId, username, password, phoneNum, address);
	}

}
