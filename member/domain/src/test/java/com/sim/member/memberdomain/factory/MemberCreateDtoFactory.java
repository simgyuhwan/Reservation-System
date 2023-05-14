package com.sim.member.memberdomain.factory;

import com.sim.member.memberdomain.dto.MemberCreateDto;

public class MemberCreateDtoFactory {
	public static MemberCreateDto createMemberCreateDto(String userId, String username, String password, String phoneNum, String address) {
		return MemberCreateDto.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
