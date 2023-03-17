package com.reservation.member.application;

import com.reservation.member.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * MemberServiceImpl.java
 * 회원 서비스 구현체
 *
 * @author sgh
 * @since 2023.03.17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Override
    public void signUp(SignUpRequest signUpRequest) {

    }
}
