package com.sim.member.memberdomain.factory;

import com.sim.member.memberdomain.dto.MemberUpdateDto;

public class MemberUpdateDtoFactory {
	public static MemberUpdateDto createMemberUpdateDto(String userId, String username, String phoneNum, String address) {
		return MemberUpdateDto.builder()
			.userId(userId)
			.username(username)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
