package com.sim.member.memberdomain.factory;

import com.sim.member.memberdomain.domain.Member;

/**
 * MemberFactory.java
 * 맴버 생성 팩토리
 *
 * @author sgh
 * @since 2023.03.23
 */
public class MemberFactory {
	public final static Long MEMBER_ID = 1L;
	public final static String USER_ID = "test";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String USERNAME = "이순신";
	public final static String ADDRESS = "서울시 마포구 창천동";
	public final static String PASSWORD = "password";

	public static Member createMember(String userId, String username) {
		return Member.of(userId, username, PASSWORD, PHONE_NUM, ADDRESS);
	}

	public static Member createMember() {
		return Member.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
	}


}
