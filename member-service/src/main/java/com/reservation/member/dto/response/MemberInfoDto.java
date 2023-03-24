package com.reservation.member.dto.response;

import com.reservation.member.domain.Member;

import lombok.Getter;

/**
 * MemberInfoDto.java
 * 회원 정보 DTO
 *
 * @author sgh
 * @since 2023.03.23
 */
@Getter
public class MemberInfoDto {
	private String userId;
	private String phoneNum;
	private String username;
	private String address;

	private MemberInfoDto(String userId, String phoneNum, String username, String address) {
		this.userId = userId;
		this.phoneNum = phoneNum;
		this.username = username;
		this.address = address;
	}

	public static MemberInfoDto of(String userId, String phoneNum, String username, String address) {
		return new MemberInfoDto(userId, phoneNum, username, address);
	}

	public static MemberInfoDto from(Member member) {
		return MemberInfoDto.of(member.getUserId(), member.getPhoneNum(), member.getUsername(), member.getAddress());
	}

}
