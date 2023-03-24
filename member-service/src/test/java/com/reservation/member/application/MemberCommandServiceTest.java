package com.reservation.member.application;

import static com.reservation.member.global.factory.MemberTestDataFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.member.application.mapper.MemberInfoDtoMapper;
import com.reservation.member.application.mapper.SignUpRequestMapper;
import com.reservation.member.dao.MemberRepository;
import com.reservation.member.domain.Member;
import com.reservation.member.dto.request.SignUpDto;
import com.reservation.member.dto.response.MemberInfoDto;
import com.reservation.member.error.DuplicateMemberException;
import com.reservation.member.error.MemberNotFoundException;
import com.reservation.member.global.factory.MemberTestDataFactory;

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

	@Mock
	private SignUpRequestMapper signUpRequestMapper;

	@Mock
	private MemberInfoDtoMapper memberInfoDtoMapper;

	@Test
	@DisplayName("회원 추가 테스트 : 회원 가입 성공 테스트")
	void signUp() {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto();
		Member member = Member.from(request);

		given(signUpRequestMapper.toEntity(request)).willReturn(member);
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

		given(signUpRequestMapper.toEntity(request)).willReturn(Member.from(request));
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
			createUpdateMemberDto()))
			.isInstanceOf(MemberNotFoundException.class);
	}

	@Test
	@DisplayName("회원 수정 테스트 : 회원 수정 성공 테스트")
	void memberModificationSuccessTest() {
		//given
		MemberInfoDto memberInfoDto = createMemberInfoDto();

		given(memberRepository.findByUserId(MemberTestDataFactory.USER_ID)).willReturn(Optional.of(createMember()));
		given(memberInfoDtoMapper.toDto((Member)any())).willReturn(memberInfoDto);

		//when
		MemberInfoDto result = memberService.updateMemberInfo(MemberTestDataFactory.USER_ID, createUpdateMemberDto());

		//then
		assertThat(result.getUserId()).isEqualTo(memberInfoDto.getUserId());
		assertThat(result.getAddress()).isEqualTo(memberInfoDto.getAddress());
		assertThat(result.getPhoneNum()).isEqualTo(memberInfoDto.getPhoneNum());
	}
}