package com.reservation.application;

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

import com.reservation.factory.MemberFactory;
import com.reservation.memberservice.application.MemberQueryServiceImpl;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
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

	@Test
	@DisplayName("회원 조회 테스트: 성공 테스트")
	void memberInquirySuccessTest() {
		//given
		Member member = MemberFactory.createMember();
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
			.isInstanceOf(InvalidUserIdException.class);
	}

	@Test
	@DisplayName("회원 조회 테스트 : userId, null 값 예외 발생 테스트")
	void invalidUserIdValueTest() {
		assertThatThrownBy(() -> memberQueryService.findMemberByUserId(null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("user id must exist");
	}
}
