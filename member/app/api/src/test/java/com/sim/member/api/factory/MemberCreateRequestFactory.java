package com.sim.member.api.factory;

import com.sim.member.dto.request.MemberCreateRequest;

public class MemberCreateRequestFactory {
	public final static String USER_ID = "test";
	public final static String USERNAME = "이순신";
	public final static String PASSWORD = "password";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String ADDRESS = "서울시 마포구 창천동";

	public static MemberCreateRequest createMemberCreateRequest() {
		return MemberCreateRequest.builder()
			.userId(USER_ID)
			.username(USERNAME)
			.phoneNum(PHONE_NUM)
			.password(PASSWORD)
			.address(ADDRESS)
			.build();
	}

	public static MemberCreateRequest createMemberCreateRequest(String userId, String username, String password, String phoneNum, String address) {
		return MemberCreateRequest.builder()
			.userId(userId)
			.username(username)
			.phoneNum(phoneNum)
			.password(password)
			.address(address)
			.build();
	}
}
