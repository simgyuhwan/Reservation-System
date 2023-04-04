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

import com.reservation.factory.MemberTestDataFactory;
import com.reservation.memberservice.application.MemberCommandServiceImpl;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.application.mapper.SignUpRequestMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.request.SignUpDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.error.DuplicateMemberException;
import com.reservation.memberservice.error.MemberNotFoundException;

/**
 * MemberService.java
 * 회원 서비스 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class MemberCommandServiceTest {

	@InjectMocks
	private MemberCommandServiceImpl memberService;

	@Mock
	private MemberRepository memberRepository;

	@Spy
	private SignUpRequestMapper signUpRequestMapper = SignUpRequestMapper.INSTANCE;

	@Spy
	private MemberInfoDtoMapper memberInfoDtoMapper = MemberInfoDtoMapper.INSTANCE;

	@Test
	@DisplayName("회원 추가 테스트 : 회원 가입 성공 테스트")
	void signUp() {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto();
		Member member = Member.from(request);

		given(memberRepository.save(any(Member.class))).willReturn(member);

		//when
		memberService.signUp(request);

		//then
		then(memberRepository).should().save(any(Member.class));
	}

	@Test
	@DisplayName("회원 추가 테스트 : 중복된 회원 예외 발생 테스트")
	void duplicateMemberRegistrationExceptionOccurs() {
		//given, when
		SignUpDto request = MemberTestDataFactory.createSignUpDto();

		given(memberRepository.existsByUserId(MemberTestDataFactory.USER_ID)).willReturn(true);

		//then
		assertThatThrownBy(() -> memberService.signUp(request))
			.isInstanceOf(DuplicateMemberException.class);
	}

	@Test
	@DisplayName("회원 수정 테스트 : 존재하지 않는 회원 예외 발생 테스트")
	void nonexistentMemberException() {
		//given
		given(memberRepository.findByUserId(MemberTestDataFactory.USER_ID)).willReturn(Optional.ofNullable(null));

		//then
		assertThatThrownBy(() -> memberService.updateMemberInfo(MemberTestDataFactory.USER_ID,
			MemberTestDataFactory.createUpdateMemberDto()))
			.isInstanceOf(MemberNotFoundException.class);
	}

	@Test
	@DisplayName("회원 수정 테스트 : 회원 수정 성공 테스트")
	void memberModificationSuccessTest() {
		//given
		MemberInfoDto memberInfoDto = MemberTestDataFactory.createMemberInfoDto();

		given(memberRepository.findByUserId(MemberTestDataFactory.USER_ID)).willReturn(Optional.of(MemberTestDataFactory.createMember()));

		//when
		MemberInfoDto result = memberService.updateMemberInfo(MemberTestDataFactory.USER_ID, MemberTestDataFactory.createUpdateMemberDto());

		//then
		assertThat(result.getUserId()).isEqualTo(memberInfoDto.getUserId());
		assertThat(result.getAddress()).isEqualTo(memberInfoDto.getAddress());
		assertThat(result.getPhoneNum()).isEqualTo(memberInfoDto.getPhoneNum());
	}
}