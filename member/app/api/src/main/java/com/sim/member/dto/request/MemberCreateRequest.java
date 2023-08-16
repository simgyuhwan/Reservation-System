package com.sim.member.dto.request;

import com.sim.member.memberdomain.dto.MemberCreateRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SignUpDto.java
 * 회원 가입 DTO
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCreateRequest {

	@Schema(description = "아이디", example = "test")
	@NotBlank(message = "아이디는 반드시 입력해야 합니다.")
	@Size(min = 3, max = 15, message = "아이디 값은 3자리에서 15자리 이하입니다.")
	private String userId;

	@Schema(description = "이름", example = "홍길동")
	@NotBlank(message = "이름은 반드시 입력해야 합니다.")
	private String username;

	@Schema(description = "비밀번호", example = "password")
	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	private String password;

	@Schema(description = "핸드폰 번호", example = "010-1234-1234")
	@NotBlank(message = "핸드폰 번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	private String phoneNum;

	@Schema(description = "주소", example = "서울시 마포구")
	@NotBlank(message = "주소는 반드시 입력해야 합니다.")
	private String address;

	public static MemberCreateRequest of(final String userId, final String username, final String password,
		final String phoneNum, final String address) {
		return new MemberCreateRequest(userId, username, password, phoneNum, address);
	}

	public MemberCreateRequestDto toDto() {
		return MemberCreateRequestDto.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}
}
