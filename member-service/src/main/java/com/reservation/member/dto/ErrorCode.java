package com.reservation.member.dto;

import lombok.Getter;

/**
 * ErrorCode.java
 * 에러 반환 코드
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public enum ErrorCode {
    SIGNUP_INPUT_VALUE_INVALID("입력 값이 올바르지 않습니다.", 400);

    private final String message;
    private final int status;

    ErrorCode( String message, int status) {
        this.message = message;
        this.status = status;
    }
}
