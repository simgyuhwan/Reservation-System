package com.sim.member.memberdomain.error;

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
	private String userId;

	public DuplicateMemberException(ErrorMessage errorMessage, String userId) {
		super(errorMessage.getMessage() + userId);
		this.userId = userId;
	}

}
