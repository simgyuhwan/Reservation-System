package com.reservation.memberservice.error;

import lombok.Getter;

/**
 * MemberNotFoundException.java
 * 회원을 조회 실패 예외
 *
 * @author sgh
 * @since 2023.03.23
 */
@Getter
public class MemberNotFoundException extends RuntimeException {
	private String userId;

	public MemberNotFoundException(String message) {
		super(message);
	}

	public MemberNotFoundException(String message, String userId) {
		super(message);
		this.userId = userId;
	}
}
