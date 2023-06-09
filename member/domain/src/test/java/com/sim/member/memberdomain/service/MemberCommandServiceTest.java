package com.sim.member.memberdomain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateDto;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;
import com.sim.member.memberdomain.error.DuplicateMemberException;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.factory.MemberCreateDtoFactory;
import com.sim.member.memberdomain.factory.MemberFactory;
import com.sim.member.memberdomain.factory.MemberUpdateDtoFactory;
import com.sim.member.memberdomain.repository.MemberRepository;

/**
 * MemberService.java
 * 회원 서비스 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class MemberCommandServiceTest {
	public final static String USER_ID = MemberFactory.USER_ID;
	public final static String USERNAME = MemberFactory.USERNAME;
	public final static String PHONE_NUM = MemberFactory.PHONE_NUM;
	public final static String ADDRESS = MemberFactory.ADDRESS;
	public final static String PASSWORD = MemberFactory.PASSWORD;

	@InjectMocks
	private MemberCommandServiceImpl memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원 추가 테스트 : 회원 가입 성공 테스트")
	void signUp() {
		//given
		MemberCreateDto memberCreateDto = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member = Member.create(memberCreateDto);

		given(memberRepository.save(any(Member.class))).willReturn(member);

		//when
		memberService.signUp(memberCreateDto);

		//then
		then(memberRepository).should().save(any(Member.class));
	}

	@Test
	@DisplayName("회원 추가 테스트 : 중복된 회원 예외 발생 테스트")
	void duplicateMemberRegistrationExceptionOccurs() {
		//given, when
		MemberCreateDto memberCreateDto = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		given(memberRepository.existsByUserId(USER_ID)).willReturn(true);

		//then
		assertThatThrownBy(() -> memberService.signUp(memberCreateDto))
			.isInstanceOf(DuplicateMemberException.class);
	}

	@Test
	@DisplayName("회원 수정 테스트 : 존재하지 않는 회원 예외 발생 테스트")
	void nonexistentMemberException() {
		//given
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(USER_ID, USERNAME, PHONE_NUM, ADDRESS);
		given(memberRepository.findByUserId(USER_ID)).willReturn(Optional.ofNullable(null));

		//then
		assertThatThrownBy(() -> memberService.updateMemberInfo(USER_ID,
			memberUpdateDto))
			.isInstanceOf(InvalidUserIdException.class);
	}

	@Test
	@DisplayName("회원 수정 테스트 : 회원 수정 성공 테스트")
	void memberModificationSuccessTest() {
		//given
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(USER_ID, USERNAME, PHONE_NUM, ADDRESS);
		Member member = createMember();

		given(memberRepository.findByUserId(USER_ID)).willReturn(Optional.of(member));

		//when
		MemberDto result = memberService.updateMemberInfo(USER_ID, memberUpdateDto);

		//then
		assertThat(result.getUserId()).isEqualTo(member.getUserId());
		assertThat(result.getAddress()).isEqualTo(member.getAddress());
		assertThat(result.getPhoneNum()).isEqualTo(member.getPhoneNum());
	}

	private MemberCreateDto createMemberCreateDto(String userId, String username, String password, String phoneNum, String address) {
		return MemberCreateDtoFactory.createMemberCreateDto(userId, username, password, phoneNum, address);
	}

	private MemberUpdateDto createMemberUpdateDto(String userId, String username, String phoneNum, String address) {
		return MemberUpdateDtoFactory.createMemberUpdateDto(userId, username, phoneNum, address);
	}

	private Member createMember() {
		return MemberFactory.createMember();
	}
}