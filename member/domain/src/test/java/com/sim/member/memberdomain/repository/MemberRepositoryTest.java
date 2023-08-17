package com.sim.member.memberdomain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sim.member.memberdomain.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * MemberRepositoryTest.java
 * 회원 Repository 테스트
 *
 * @author sgh
 * @since 2023.03.16
 */
@DataJpaTest
public class MemberRepositoryTest {
	public final static String USER_ID = "test";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String USERNAME = "이순신";
	public final static String ADDRESS = "서울시 마포구 창천동";
	public final static String PASSWORD = "password";

	@Autowired
	private MemberRepository memberRepository;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DisplayName("회원 정보가 등록된다.")
	void memberRegistration() {
		// given
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		// when
		final Member saveMember = memberRepository.save(member);

		// then
		assertThat(saveMember.getId()).isNotNull();
		assertThat(saveMember.getUserId()).isEqualTo(USER_ID);
		assertThat(saveMember.getUsername()).isEqualTo(USERNAME);
		assertThat(saveMember.getPassword()).isEqualTo(PASSWORD);
		assertThat(saveMember.getPhoneNum()).isEqualTo(PHONE_NUM);
		assertThat(saveMember.getAddress()).isEqualTo(ADDRESS);
	}

	@Test
	@DisplayName("회원 정보가 수정된다.")
	void editMember() {
		//given
		String userNameToChange = "changed_user";
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when
		member.changeName(userNameToChange);
		Member result = memberRepository.findById(member.getId()).orElseThrow();

		//then
		assertThat(result.getUsername()).isEqualTo(userNameToChange);
	}

	@Test
	@DisplayName("회원 정보가 삭제된다.")
	void deleteMember() {
		//given
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when
		memberRepository.delete(member);
		boolean result = memberRepository.existsById(member.getId());

		//then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("회원을 중복 등록하면 예외가 발생한다.")
	void memberDuplicateException() {
		//given
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member duplicateMember = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when,then
		assertThatThrownBy(() -> memberRepository.save(duplicateMember))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	@DisplayName("회원 이름으로 조회가 가능하다.")
	void nameLookup() {
		//given
		memberRepository.save(createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS));

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
	@DisplayName("등록되지 않는 회원 이름으로 조회시 예외가 발생한다.")
	void missingNameLookupException() {
		// when
		String unsavedName = "unsaved_Name";
		Optional<Member> member = memberRepository.findByUserId(unsavedName);

		// given, then
		assertThatThrownBy(member::get)
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("핸드폰 번호로 조회가 가능하다.")
	void inquiryThroughPhoneNumber() {
		//given
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when
		Member findMember = memberRepository.findByPhoneNum(PHONE_NUM).orElseThrow();

		//then
		assertThat(findMember.getPhoneNum()).isEqualTo(PHONE_NUM);
		assertThat(findMember.getAddress()).isEqualTo(ADDRESS);
		assertThat(findMember.getUserId()).isEqualTo(USER_ID);
		assertThat(findMember.getUsername()).isEqualTo(USERNAME);
	}

	@Test
	@DisplayName("등록되지 않은 핸드폰 번호로 조회시 예외가 발생한다.")
	void nonExistentPhoneNumberLookupFailureException() {
		// when
		String unsavedPhoneNum = "000-0000-0000";
		Optional<Member> member = memberRepository.findByPhoneNum(unsavedPhoneNum);

		// given, then
		assertThatThrownBy(member::get)
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("회원 전체 조회가 가능하다.")
	void checkAllMemberCountsMatch() {
		//given
		createAndSaveMemberList();

		//when
		List<Member> fetchedMembers = memberRepository.findAll();

		//then
		assertThat(fetchedMembers).hasSize(3);
	}

	@Test
	@DisplayName("모든 회원 삭제가 가능하다.")
	void checkZeroViewsWhenDeletingAllMembers() {
		//given
		createAndSaveMemberList();

		//when
		memberRepository.deleteAll();
		List<Member> fetchedMembers = memberRepository.findAll();

		//then
		assertThat(fetchedMembers).isEmpty();
	}

	@Test
	@DisplayName("등록된 회원 존재유무 확인이 가능하다.")
	void registeredMemberVerificationTest() {
		//given
		Member member = createMember(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.save(member);

		//when
		boolean result = memberRepository.existsByUserId(member.getUserId());

		//then
		assertThat(result).isTrue();
	}

	private Member createMember(String userId, String username, String password, String phoneNum, String address) {
		return Member.of(userId, username, password, phoneNum, address);
	}

	private void createAndSaveMemberList() {
		Member member1 = createMember(USER_ID + "1", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member2 = createMember(USER_ID + "2", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		Member member3 = createMember(USER_ID + "3", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
		memberRepository.saveAll(List.of(member1, member2, member3));
	}

}