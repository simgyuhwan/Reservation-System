package com.sim.member.memberdomain.dto;

import com.sim.member.memberdomain.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDto {
	private Long id;
	private String userId;
	private String username;
	private String phoneNum;
	private String address;

	public static MemberDto of(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.userId(member.getUserId())
			.phoneNum(member.getPhoneNum())
			.username(member.getUsername())
			.address(member.getAddress())
			.build();
	}
	public static MemberDto of(String userId, String username, String phoneNum, String address) {
		return MemberDto.builder()
			.userId(userId)
			.username(username)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
