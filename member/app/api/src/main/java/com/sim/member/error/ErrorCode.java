package com.sim.member.error;

import lombok.Getter;

/**
 * ErrorCode.java
 * HTTP 에러 반환 메시지 및 코드
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public enum ErrorCode {
	// member
	SIGNUP_INPUT_VALUE_INVALID("입력 값이 올바르지 않습니다.", 400),
	DUPLICATE_MEMBER_ID_VALUE("중복된 회원 ID 입니다.", 409),
	MEMBER_NOT_FOUND("일치하는 회원이 없습니다", 404),
	INVALID_USER_ID_VALUE("유효하지 않은 회원 ID입니다.", 400);

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
