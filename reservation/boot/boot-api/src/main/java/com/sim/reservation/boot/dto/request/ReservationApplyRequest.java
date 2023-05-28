package com.sim.reservation.boot.dto.request;

import com.sim.reservation.data.reservation.dto.ReservationDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReservationApplyRequest.java
 * 예약 신청 Request
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationApplyRequest {
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

	private Boolean isEmailReceiveDenied;
	private Boolean isSmsReceiveDenied;

	@Builder
	public ReservationApplyRequest(String userId, String name, String phoneNum, String email,
		boolean isEmailReceiveDenied,
		boolean isSmsReceiveDenied) {
		this.userId = userId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSmsReceiveDenied = isSmsReceiveDenied;
	}

	public ReservationDto toReservationDto() {
		return ReservationDto.builder()
			.userId(userId)
			.name(name)
			.phoneNum(phoneNum)
			.email(email)
			.isEmailReceiveDenied(isEmailReceiveDenied)
			.isSmsReceiveDenied(isSmsReceiveDenied)
			.build();
	}
}
