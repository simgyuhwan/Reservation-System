package com.sim.member.memberdomain.dto;

import com.sim.member.memberdomain.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequestDto {
	private String userId;
	private String username;
	private String password;
	private String phoneNum;
	private String address;

	public Member toEntity() {
		return Member.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
