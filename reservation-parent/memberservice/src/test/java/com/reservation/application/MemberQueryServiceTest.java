package com.reservation.application;

import static com.reservation.factory.MemberTestConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.factory.MemberTestDataFactory;
import com.reservation.memberservice.application.MemberQueryServiceImpl;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.error.MemberNotFoundException;

/**
 * MemberQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberQueryServiceTest {

	@InjectMocks
	private MemberQueryServiceImpl memberQueryService;

	@Mock
	private MemberRepository memberRepository;

	@Spy
	MemberInfoDtoMapper memberInfoDtoMapper = MemberInfoDtoMapper.INSTANCE;

	@Test
	@DisplayName("회원 조회 테스트: 성공 테스트")
	void memberInquirySuccessTest() {
		//given
		Member member = MemberTestDataFactory.createMember();
		given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.of(member));

		//when
		MemberInfoDto findMemberDto = memberQueryService.findMemberByUserId(member.getUserId());

		//then
		assertThat(findMemberDto.getUserId()).isEqualTo(USER_ID);
	}

	@Test
	@DisplayName("회원 조회 테스트 : 일치하는 userId 없음 예외 발생 테스트")
	void noMembersMatchingException() {
		//given
		given(memberRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));

		//when
		assertThatThrownBy(() -> memberQueryService.findMemberByUserId(USER_ID))
			.isInstanceOf(MemberNotFoundException.class);
	}

	@Test
	@DisplayName("회원 조회 테스트 : userId, null 값 예외 발생 테스트")
	void invalidUserIdValueTest() {
		assertThatThrownBy(() -> memberQueryService.findMemberByUserId(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("user id must exist");
	}
}
