package com.sim.member.memberdomain.dto;

import java.util.ArrayList;
import java.util.List;

import com.sim.member.memberdomain.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberPerformanceDto {
	private String userId;
	private String userName;

	@Builder.Default
	private List<PerformanceDto> performances = new ArrayList<>();

	public static MemberPerformanceDto of(Member member, List<PerformanceDto> performances) {
		return MemberPerformanceDto.builder()
			.userId(member.getUserId())
			.userName(member.getUsername())
			.performances(performances)
			.build();
	}
}
