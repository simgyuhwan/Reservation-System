package com.reservation.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UpdateMemberDto.java
 * 회원 정보 수정 DTO
 *
 * @author sgh
 * @since 2023.03.24
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class UpdateMemberDto {
	@NotBlank(message = "아이디 값은 반드시 필요합니다")
	private String userId;

	@NotBlank(message = "핸드폰 번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	private String phoneNum;

	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	private String username;
	
	@NotBlank(message = "주소는 반드시 입력해야 합니다.")
	private String address;

	private UpdateMemberDto(String userId, String phoneNum, String username, String address) {
		this.userId = userId;
		this.username = username;
		this.address = address;
		this.phoneNum = phoneNum;
	}

	public static UpdateMemberDto of(String userId, String phoneNum, String username, String address) {
		return new UpdateMemberDto(userId, phoneNum, username, address);
	}
}
