package com.reservation.factory;

import com.reservation.memberservice.dto.request.SignUpDto;

/**
 * SignUpDtoFactory.java
 * SignUpDto 객체 Factory 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
public class SignUpDtoFactory {
    public final static String USER_ID = "test";
    public final static String PHONE_NUM = "010-1111-9999";
    public final static String USERNAME = "이순신";
    public final static String ADDRESS = "서울시 마포구 창천동";
    public final static String PASSWORD = "password";

    public static SignUpDto createSignUpDto() {
        return SignUpDto.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
    }

    public static SignUpDto createSignUpDto(String userId, String username, String password, String phoneNum,
        String address) {
        return SignUpDto.of(userId, username, password, phoneNum, address);
    }
}
