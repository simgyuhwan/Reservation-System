package com.reservation.member.application;

import com.reservation.member.dto.SignUpRequest;

/**
 * MemberService.java
 * 회원 서비스
 *
 * @author sgh
 * @since 2023.03.17
 */
public interface MemberService {

    /**
     * 회원 가입
     */
    void signUp(SignUpRequest signUpRequest);
}
