package com.sim.member.memberdomain.service;

import com.sim.member.memberdomain.IntegrationTestSupport;
import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;
import com.sim.member.memberdomain.error.DuplicateMemberException;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.factory.MemberFactory;
import com.sim.member.memberdomain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * MemberService.java
 * 회원 서비스 테스트 (통합 테스트)
 *
 * @author sgh
 * @since 2023.03.17
 */
public class MemberCommandServiceIntegratedTest extends IntegrationTestSupport {
	public final static String USER_ID = MemberFactory.USER_ID;
	public final static String USERNAME = MemberFactory.USERNAME;
	public final static String PHONE_NUM = MemberFactory.PHONE_NUM;
	public final static String ADDRESS = MemberFactory.ADDRESS;
	public final static String PASSWORD = MemberFactory.PASSWORD;

    @Autowired
	private MemberCommandServiceImpl memberService;

    @Autowired
	private MemberRepository memberRepository;

    @AfterEach
    void setUp() {
        memberRepository.deleteAllInBatch();
    }

    @Test
	@DisplayName("회원 정보를 받아 회원을 생성한다.")
	void signUp() {
		//given
		MemberCreateRequestDto memberCreateRequestDto = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		//when
        MemberDto memberDto = memberService.signUp(memberCreateRequestDto);

        //then
        assertThat(memberDto.getId()).isNotNull();
		assertThat(memberDto.getUsername()).isEqualTo(USERNAME);
		assertThat(memberDto.getAddress()).isEqualTo(ADDRESS);
	}

	@Test
	@DisplayName("이미 등록된 회원 정보로는 회원 생성시 예외가 발생한다.")
	void duplicateMemberRegistrationExceptionOccurs() {
		//given, when
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);
		MemberCreateRequestDto memberCreateRequestDto = createMemberCreateDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		//then
		assertThatThrownBy(() -> memberService.signUp(memberCreateRequestDto))
			.isInstanceOf(DuplicateMemberException.class);
	}

	@Test
	@DisplayName("등록되지 않은 회원 정보 수정시 예외가 발생한다.")
	void nonexistentMemberException() {
		//given
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(USER_ID, USERNAME, PHONE_NUM, ADDRESS);

		//then
		assertThatThrownBy(() -> memberService.updateMemberInfo(USER_ID,
			memberUpdateDto))
			.isInstanceOf(InvalidUserIdException.class);
	}

	@Test
	@DisplayName("회원 수정 요청을 통해 회원 정보 수정이 가능하다.")
	void memberModificationSuccessTest() {
		//given
		MemberUpdateDto memberUpdateDto = createMemberUpdateDto(USER_ID, USERNAME, PHONE_NUM, ADDRESS);
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when
		MemberDto result = memberService.updateMemberInfo(USER_ID, memberUpdateDto);

		//then
		assertThat(result.getUserId()).isEqualTo(member.getUserId());
		assertThat(result.getAddress()).isEqualTo(member.getAddress());
		assertThat(result.getPhoneNum()).isEqualTo(member.getPhoneNum());
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

	private Member createMember(String userId, String username, String password, String phoneNum, String address) {
		return Member.of(userId, username, password, phoneNum, address);
	}
}