package com.reservation.member.error;

import lombok.Getter;

/**
 * MemberNotFoundException.java
 * Class 설명을 작성하세요.
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
