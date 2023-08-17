package com.sim.member.memberdomain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;
import com.sim.member.memberdomain.error.DuplicateMemberException;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.repository.MemberRepository;

/**
 * MemberCommandServiceUnitTest.java
 * 회원 서비스 테스트 (단위 테스트)
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class MemberCommandServiceUnitTest {
	public final static String USER_ID = "test";
	public final static String USERNAME = "이순신";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String ADDRESS = "서울시 마포구 창천동";
	public final static String PASSWORD = "password";

	@InjectMocks
	private MemberCommandServiceImpl memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원 가입이 가능하다.")
	void signUp() {
		//given
		MemberCreateRequestDto memberCreateRequest = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member = Member.create(memberCreateRequest);

		given(memberRepository.save(any(Member.class)))
				.willReturn(member);

		//when
		memberService.signUp(memberCreateRequest);

		//then
		then(memberRepository).should()
				.save(member);
	}

	@Test
	@DisplayName("중복된 회원 등록 요청은 불가능하다.")
	void duplicateMemberRegistrationExceptionOccurs() {
		//given, when
		MemberCreateRequestDto memberCreateRequestDto = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		given(memberRepository.existsByUserId(USER_ID))
				.willReturn(true);

		//then
		assertThatThrownBy(() -> memberService.signUp(memberCreateRequestDto))
			.isInstanceOf(DuplicateMemberException.class);
	}

	@Test
	@DisplayName("등록되지 않은 회원의 수정은 불가능하다.")
	void nonexistentMemberException() {
		//given
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(USER_ID, USERNAME, PHONE_NUM, ADDRESS);

		given(memberRepository.findByUserId(USER_ID))
				.willReturn(Optional.empty());

		//then
		assertThatThrownBy(() -> memberService.updateMemberInfo(USER_ID,
			memberUpdateDto))
			.isInstanceOf(InvalidUserIdException.class);
	}

	@Test
	@DisplayName("회원 수정이 가능하다.")
	void memberModificationSuccessTest() {
		//given
		String newUserId = "newUserId";
		String newUserName = "newUserName";
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(newUserId, newUserName, "newPhoneNum", "newAddress");
		Member member = Member.of(USER_ID,"oldUserName", "oldPassword", "oldPhoneNum", "oldAddress");

		//when
		when(memberRepository.findByUserId(USER_ID)).thenReturn(Optional.of(member));
		MemberDto actualMemberDto = memberService.updateMemberInfo(USER_ID, memberUpdateDto);

		//then
		verify(memberRepository).findByUserId(USER_ID);
		assertThat(actualMemberDto.getUsername()).isEqualTo(newUserName);
		assertThat(actualMemberDto.getUsername()).isEqualTo(newUserName);
	}

	private MemberCreateRequestDto createMemberCreateDto(String userId, String username, String password, String phoneNum, String address) {
		return MemberCreateRequestDto.builder()
				.userId(userId)
				.username(username)
				.password(password)
				.phoneNum(phoneNum)
				.address(address)
				.build();
	}

	private MemberUpdateDto createMemberUpdateDto(String userId, String username, String phoneNum, String address) {
		return MemberUpdateDto.builder()
				.userId(userId)
				.username(username)
				.phoneNum(phoneNum)
				.address(address)
				.build();
	}

}