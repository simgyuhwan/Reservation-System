package com.reservation.member.application;

import com.reservation.member.application.mapper.SignUpRequestMapper;
import com.reservation.member.dao.MemberRepository;
import com.reservation.member.domain.Member;
import com.reservation.member.dto.SignUpRequest;
import com.reservation.member.exception.DuplicateMemberException;
import com.reservation.member.factory.SignUpFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

/**
 * MemberService.java
 * 회원 서비스 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {

    private final static String USER_ID = "firstUser";
    private final static String USERNAME = "이순신";
    private final static String PASSWORD = "password";
    private final static String PHONE_NUM = "010-8988-9999";
    private final static String ADDRESS = "경기도 한국군 한국리";

    @InjectMocks
    private MemberCreator memberCreator;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SignUpRequestMapper mapper;

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void signUp() {
        //given
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
        Member member = Member.from(request);

        given(mapper.toEntity(request)).willReturn(member);
        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        memberCreator.create(request);

        //then
        then(memberRepository).should().save(any(Member.class));
    }

    @Test
    @DisplayName("중복된 회원 예외 발생 테스트")
    void duplicateMemberRegistrationExceptionOccurs() {
        //given
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

        given(mapper.toEntity(request)).willReturn(Member.from(request));
        given(memberRepository.existsByUserId(USER_ID)).willReturn(true);

        //when
        assertThatThrownBy(() -> memberCreator.create(request))
                .isInstanceOf(DuplicateMemberException.class);
    }

}