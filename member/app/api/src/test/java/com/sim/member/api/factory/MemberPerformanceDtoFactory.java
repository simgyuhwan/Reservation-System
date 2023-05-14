package com.sim.member.api.factory;

import java.util.ArrayList;

import com.sim.member.memberdomain.dto.MemberPerformanceDto;

public class MemberPerformanceDtoFactory {
	public final static String USER_ID = "test";
	public final static String USERNAME = "이순신";

	public static MemberPerformanceDto createMemberPerformanceDto() {
		return MemberPerformanceDto.builder()
			.userId(USER_ID)
			.userName(USERNAME)
			.performances(new ArrayList<>())
			.build();
	}

	public static MemberPerformanceDto createMemberPerformanceDto(String userId, String userName) {
		return MemberPerformanceDto.builder()
			.userId(userId)
			.userName(userName)
			.performances(new ArrayList<>())
			.build();
	}
}
