package com.sim.member.memberdomain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sim.member.memberdomain.IntegrationTestSupport;
import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.repository.MemberRepository;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * MemberQueryServiceIntegrationTest.java
 *
 * @author sgh
 * @since 2023.03.23
 */
public class MemberQueryServiceIntegrationTest extends IntegrationTestSupport {

  private final static Long MEMBER_ID = 1L;
  private final static String USER_ID = "test";
  private final static String USERNAME = "이순신";
  private final static String ADDRESS = "서울시 마포구 창천동";
  private final static String PHONE_NUM = "010-1111-9999";
  private final static String PASSWORD = "password";

  @Autowired
  private MemberQueryServiceImpl memberQueryService;

  @Autowired
  private MemberRepository memberRepository;

  @AfterEach
  void tearDown() {
    memberRepository.deleteAllInBatch();
  }

  @Nested
  @DisplayName("회원 정보 조회")
  class ViewMemberDetailsByMemberIdTest {

    @Test
    @DisplayName("회원 ID로 회원 정보 조회가 가능하다.")
    void memberInquirySuccessTest() {
      //given
      creatAndSaveMember(USER_ID, USERNAME);

      //when
      MemberDto memberDto = memberQueryService.findMemberByUserId(USER_ID);

      //then
      assertThat(memberDto.getUserId()).isEqualTo(USER_ID);
      assertThat(memberDto.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    @DisplayName("등록되지 않은 회원 조회시 예외가 발생한다.")
    void noMembersMatchingException() {
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
          Arguments.of((Object) null)
      );
    }
  }

  @Nested
  @DisplayName("회원 ID로 공연 조회")
  class PerformanceSearchByMemberIdTest {

    @Test
    @DisplayName("등록되지 않은 Member ID로 조회시 예외가 발생한다.")
    void invalidIDLookupExceptionOccurred() {
      assertThatThrownBy(() -> memberQueryService.selectPerformancesById(MEMBER_ID))
          .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("Member ID가 null 일 시 예외가 발생한다.")
    void invalidUserIdValueTest() {
      assertThatThrownBy(() -> memberQueryService.selectPerformancesById(null))
          .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
  }

  private void creatAndSaveMember(String userId, String username) {
    memberRepository.save(Member.of(userId, username, PASSWORD, PHONE_NUM, ADDRESS));
  }

}
