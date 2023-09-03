package com.sim.member.memberdomain.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

/**
 * Created by Gyuhwan
 */
class MemberTest {
    private final static String USER_ID = "test";
    private final static String PHONE_NUM = "010-1111-2222";
    private final static String ADDRESS = "서울시 마포구";
    private final static String PASSWORD = "12345678";
    private final static String USERNAME = "홍길동";

    @DisplayName("회원 도메인을 생성할 수 있다.")
    @Test
    void canCreateAMemberDomain() {
        Member member = Member.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

        assertThat(member.getUserId()).isEqualTo(USER_ID);
        assertThat(member.getUsername()).isEqualTo(USERNAME);
        assertThat(member.getPassword()).isEqualTo(PASSWORD);
    }

    @DisplayName("회원 정보를 수정할 수 있다.")
    @Test
    void canChangeYourMemberInformation() {
        // given
        Member beforeMember = createMember(USERNAME, ADDRESS, PHONE_NUM);
        String changedName = "이순신";
        String changeAddress = "서울시 강남구";
        String changePhoneNum = "010-1123-2234";

        // when
        beforeMember.updateInfo(changedName, changeAddress, changePhoneNum);

        // then
        assertThat(beforeMember.getUsername()).isEqualTo(changedName);
        assertThat(beforeMember.getAddress()).isEqualTo(changeAddress);
        assertThat(beforeMember.getPhoneNum()).isEqualTo(changePhoneNum);
    }

    private static Member createMember(String username, String address, String phoneNum) {
        return Member.builder()
            .userId(USER_ID)
            .username(username)
            .address(address)
            .phoneNum(phoneNum)
            .password(PASSWORD)
            .build();
    }

    @DisplayName("회원 이름을 수정할 수 있다.")
    @Test
    void canEditTheMemberName() {
        // given
        Member member = createMember(USERNAME, ADDRESS, PHONE_NUM);
        String changedName = "이순신";

        // when
        member.changeName(changedName);

        // then
        assertThat(member.getUsername()).isEqualTo(changedName);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원 이름을 수정할 때, 수정할 이름은 반드시 포함되어야 한다.")
    void nameToChangeIsRequired(String changedName) {
        Member member = createMember(USERNAME, ADDRESS, PHONE_NUM);

        assertThatThrownBy(() -> member.changeName(changedName))
            .isInstanceOf(IllegalArgumentException.class);
    }

}