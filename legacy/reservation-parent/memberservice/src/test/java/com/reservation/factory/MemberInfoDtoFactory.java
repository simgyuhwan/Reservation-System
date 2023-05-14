package com.reservation.factory;

import com.reservation.memberservice.dto.response.MemberInfoDto;

/**
 * MemberInfoDto Factory 클래스
 */
public class MemberInfoDtoFactory {
	public static final String USER_ID = "test";
	public static final String USERNAME = "홍길동";
	public static final String PHONE_NUM = "010-1234-1234";
	public static final String ADDRESS = "서울시 강남구";

	public static MemberInfoDto createMemberInfoDto(String userId, String username) {
		return MemberInfoDto.of(userId, PHONE_NUM, username, ADDRESS);
	}

	public static MemberInfoDto createMemberInfoDto() {
		return MemberInfoDto.of(USER_ID, PHONE_NUM, USERNAME, ADDRESS);
	}
}
