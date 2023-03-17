package com.reservation.member.factory;

import com.reservation.member.dto.SignUpRequest;

/**
 * SignUpFactory.java
 * 회원 가입 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.03.17
 */
public class SignUpFactory {
    public static SignUpRequest 회원가입_DTO_생성(String userId, String username, String password,
                                            String phoneNum, String address) {
        return SignUpRequest.of(userId, username, password, phoneNum, address);
    }
}
