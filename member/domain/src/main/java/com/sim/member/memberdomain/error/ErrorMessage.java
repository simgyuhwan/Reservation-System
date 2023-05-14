package com.sim.member.memberdomain.error;

import lombok.Getter;

/**
 * ErrorMessage.java
 * 에러 메시지
 *
 * @author sgh
 * @since 2023.04.27
 */
@Getter
public enum ErrorMessage {
	MEMBER_NOT_FOUND("Member 조회 실패, memberId : "),
	EVENT_NOT_FOUND("Event 조회 실패, event id : "),
	INVALID_USER_ID("잘못된 회원 ID, userId : "),
	DUPLICATE_MEMBER("중복된 회원 ID, userId : ");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
