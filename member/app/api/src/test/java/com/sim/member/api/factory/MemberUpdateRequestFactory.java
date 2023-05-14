package com.sim.member.api.factory;

import com.sim.member.dto.request.MemberUpdateRequest;

public class MemberUpdateRequestFactory {
	public final static String USER_ID = "test";
	public final static String USERNAME = "이순신";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String ADDRESS = "서울시 마포구 창천동";

	public static MemberUpdateRequest createMemberUpdateRequest(String userId, String username, String phoneNum, String address) {
		return MemberUpdateRequest.of(userId, username, phoneNum, address);
	}

	public static MemberUpdateRequest createMemberUpdateRequest() {
		return MemberUpdateRequest.of(USER_ID, USERNAME, PHONE_NUM, ADDRESS);
	}
}
