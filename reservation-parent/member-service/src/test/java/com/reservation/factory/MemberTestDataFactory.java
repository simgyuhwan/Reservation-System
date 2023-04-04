package com.reservation.factory;

import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.request.SignUpDto;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;

/**
 * MemberFactory.java
 * 맴버 생성 팩토리
 *
 * @author sgh
 * @since 2023.03.23
 */
public class MemberTestDataFactory {
	public final static String USER_ID = "test";
	public final static String PHONE_NUM = "010-1111-9999";
	public final static String USERNAME = "이순신";
	public final static String ADDRESS = "서울시 마포구 창천동";
	public final static String PASSWORD = "password";

	public static Member createMember() {
		return Member.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
	}

	public static MemberInfoDto createMemberInfoDto() {
		return MemberInfoDto.of(USER_ID, PHONE_NUM, USERNAME, ADDRESS);
	}

	public static UpdateMemberDto createUpdateMemberDto() {
		return UpdateMemberDto.of(USER_ID, PHONE_NUM, USERNAME, ADDRESS);
	}

	public static UpdateMemberDto createUpdateMemberDto(String userId, String phoneNum, String username,
		String address) {
		return UpdateMemberDto.of(userId, phoneNum, username, address);
	}

	public static SignUpDto createSignUpDto() {
		return SignUpDto.of(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);
	}

	public static SignUpDto createSignUpDto(String userId, String username, String password, String phoneNum,
		String address) {
		return SignUpDto.of(userId, username, password, phoneNum, address);
	}
}
