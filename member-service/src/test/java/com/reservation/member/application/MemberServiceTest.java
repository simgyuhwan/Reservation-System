package com.reservation.member.application;

import com.reservation.member.dao.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * MemberService.java
 * 회원 서비스 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void signUp() {
        //given

        //when

        //then
    }
}