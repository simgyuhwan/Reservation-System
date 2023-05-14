package com.sim.member.memberdomain.error;

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
	private long id;

	public MemberNotFoundException(ErrorMessage message, Long memberId) {
		super(message.name() + memberId);
		this.id = memberId;
	}
}
