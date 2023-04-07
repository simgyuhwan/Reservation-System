package com.reservation.memberservice.dto.response;

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
public class MemberInfoDto {
	private String userId;
	private String phoneNum;
	private String username;
	private String address;

	public static MemberInfoDto of(String userId, String phoneNum, String username, String address) {
		return new MemberInfoDto(userId, phoneNum, username, address);
	}
}
