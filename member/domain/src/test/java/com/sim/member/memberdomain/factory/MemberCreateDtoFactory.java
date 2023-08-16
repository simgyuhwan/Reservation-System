package com.sim.member.memberdomain.factory;

import com.sim.member.memberdomain.dto.MemberCreateRequestDto;

public class MemberCreateDtoFactory {
	public static MemberCreateRequestDto createMemberCreateDto(String userId, String username, String password, String phoneNum, String address) {
		return MemberCreateRequestDto.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
