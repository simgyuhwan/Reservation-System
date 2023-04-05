package com.reservation.member.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.member.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * MemberRepositoryTest.java
 * 회원 Repository 테스트
 *
 * @author sgh
 * @since 2023.03.16
 */
@Transactional
@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@PersistenceContext
	private EntityManager em;

	private static final String USER_ID = "user";
	private static final String USERNAME = "first_user";
	private static final String PASSWORD = "password";
	private static final String PHONE_NUM = "010-8888-8888";
	private static final String ADDRESS = "경기도 북극읍 한국리";

	@Test
	@DisplayName("회원 등록 테스트")
	void memberRegistration() {
		// given
		Member member = 회원생성();

		// when
		final Member saveMember = memberRepository.save(member);

		// then
		assertThat(saveMember.getId()).isNotNull();
		assertThat(saveMember.getUsername()).isEqualTo(USERNAME);
		assertThat(saveMember.getPassword()).isEqualTo(PASSWORD);
		assertThat(saveMember.getPhoneNum()).isEqualTo(PHONE_NUM);
		assertThat(saveMember.getAddress()).isEqualTo(ADDRESS);
	}

	@Test
	@DisplayName("회원 수정 테스트")
	void editMember() {
		//given
		Member member = 회원생성();
		memberRepository.save(member);

		//when
		String newUsername = "second_user";
		member.changeName(newUsername);
		memberRepository.save(member);

		//then
		Member result = memberRepository.findById(member.getId()).orElseThrow();
		assertThat(result.getUsername()).isEqualTo(newUsername);
	}

	@Test
	@DisplayName("회원 삭제 테스트")
	void deleteMember() {
		//given
		Member member = 회원생성();
		memberRepository.save(member);

		//when
		memberRepository.delete(member);

		//then
		assertThatThrownBy(() -> memberRepository.findById(member.getId()).get())
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("회원 중복 등록시 예외 발생 테스트")
	void memberDuplicateException() {
		//given
		memberRepository.save(회원생성());

		//when,then
		assertThatThrownBy(() -> memberRepository.save(회원생성()))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("회원 이름를 이용한 조회 시, 저장된 회원 정보 일치 테스트")
	void nameLookup() {
		//given
		memberRepository.save(회원생성());

		//when
		Member findMember = memberRepository.findByUsername(USERNAME)
			.orElseThrow();

		//then
		assertThat(findMember.getUsername()).isEqualTo(USERNAME);
		assertThat(findMember.getPassword()).isEqualTo(PASSWORD);
		assertThat(findMember.getUserId()).isEqualTo(USER_ID);
		assertThat(findMember.getPhoneNum()).isEqualTo(PHONE_NUM);
	}

	@Test
	@DisplayName("존재 하지 않는 이름으로 회원 조회시 예외 발생 테스트")
	void missingNameLookupException() {
		assertThatThrownBy(() -> memberRepository.findByUsername(USERNAME).get())
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("폰 번호를 이용한 조회 시, 저장된 회원 정보 일치 테스트")
	void inquiryThroughPhoneNumber() {
		//given
		Member member = 회원생성();
		memberRepository.save(member);

		//when
		Member findMember = memberRepository.findByPhoneNum(PHONE_NUM).get();

		//then
		assertThat(findMember.getPhoneNum()).isEqualTo(PHONE_NUM);
		assertThat(findMember.getAddress()).isEqualTo(ADDRESS);
		assertThat(findMember.getUserId()).isEqualTo(USER_ID);
		assertThat(findMember.getUsername()).isEqualTo(USERNAME);
	}

	@Test
	@DisplayName("존재하지 않는 폰 번호 조회 시, 예외 발생 테스트")
	void nonExistentPhoneNumberLookupFailureException() {
		assertThatThrownBy(() -> memberRepository.findByPhoneNum(PHONE_NUM).get())
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("모든 회원 수 조회 시, 저장된 회원 수와 일치 테스트")
	void checkAllMemberCountsMatch() {
		//given
		회원리스트생성_및_저장();

		//when
		List<Member> fetchedMembers = memberRepository.findAll();

		//then
		assertThat(fetchedMembers.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("모든 맴버 삭제 시, 맴버 수 0 확인 테스트")
	void checkZeroViewsWhenDeletingAllMembers() {
		//given
		회원리스트생성_및_저장();

		//when
		memberRepository.deleteAll();
		List<Member> fetchedMembers = memberRepository.findAll();

		//then
		assertThat(fetchedMembers.isEmpty()).isTrue();
	}

	@Test
	@DisplayName("등록된 회원 확인 테스트")
	void registeredMemberVerificationTest() {
		//given
		Member member = 회원생성();
		memberRepository.save(member);

		//when
		boolean result = memberRepository.existsByUserId(member.getUserId());

		//then
		assertThat(result).isTrue();
	}

	private Member 회원생성() {
		return Member.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
	}

	private Member createMember(String userId, String username, String password, String phoneNum, String address) {
		return Member.of(userId, username, password, phoneNum, address);
	}

	private void 회원리스트생성_및_저장() {
		Member member1 = createMember(USER_ID + "1", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member2 = createMember(USER_ID + "2", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member3 = createMember(USER_ID + "3", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.saveAll(List.of(member1, member2, member3));
	}

}
