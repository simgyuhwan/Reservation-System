package com.reservation.memberservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateMemberDto {

	@Schema(description = "아이디", example = "test")
	@NotBlank(message = "아이디 값은 반드시 필요합니다")
	private String userId;

	@Schema(description = "핸드폰 번호", example = "010-1234-1234")
	@NotBlank(message = "핸드폰 번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	private String phoneNum;

	@Schema(description = "이름", example = "홍길동")
	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	private String username;

	@Schema(description = "주소", example = "서울시 강남구")
	@NotBlank(message = "주소는 반드시 입력해야 합니다.")
	private String address;

	public static UpdateMemberDto of(String userId, String phoneNum, String username, String address) {
		return new UpdateMemberDto(userId, phoneNum, username, address);
	}
}
