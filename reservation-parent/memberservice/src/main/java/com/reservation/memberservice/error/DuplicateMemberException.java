package com.reservation.memberservice.error;

import lombok.Getter;

/**
 * DuplicationMemberException.java
 * 중복된 회원 예외
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public class DuplicateMemberException extends RuntimeException {
	public DuplicateMemberException() {
		super();
	}

	public DuplicateMemberException(String message) {
		super(message);
	}
}
