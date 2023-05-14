package com.sim.member.dto.response;

import com.sim.member.memberdomain.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MemberInfoDto.java
 * 회원 정보 DTO
 *
 * @author sgh
 * @since 2023.03.23
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
	private String userId;
	private String phoneNum;
	private String username;
	private String address;

	public static MemberInfoResponse from(MemberDto memberDto) {
		return MemberInfoResponse.builder()
			.userId(memberDto.getUserId())
			.phoneNum(memberDto.getPhoneNum())
			.username(memberDto.getUsername())
			.address(memberDto.getAddress())
			.build();
	}
}
