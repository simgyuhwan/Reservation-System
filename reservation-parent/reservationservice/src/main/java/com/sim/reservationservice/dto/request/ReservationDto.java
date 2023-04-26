package com.sim.reservationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReservationDto.java
 * 공연 예약 관련 DTO
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationDto {
	@NotBlank(message = "회원 아이디는 필수입니다.")
	private String userId;

	@NotBlank(message = "예약자 이름은 필수입니다.")
	private String name;

	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	@NotBlank(message = "예약자 핸드폰 번호는 필수입니다.")
	private String phoneNum;

	@Email(message = "이메일 형식에 맞지 않습니다.")
	@NotBlank(message = "예약자 이메일은 필수입니다.")
	private String email;

	private boolean isEmailReceiveDenied;
	private boolean isSnsReceiveDenied;

	@Builder
	public ReservationDto(String userId, String name, String phoneNum, String email, boolean isEmailReceiveDenied,
		boolean isSnsReceiveDenied) {
		this.userId = userId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSnsReceiveDenied = isSnsReceiveDenied;
	}
}
