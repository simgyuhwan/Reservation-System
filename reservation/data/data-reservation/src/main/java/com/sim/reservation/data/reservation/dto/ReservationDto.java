package com.sim.reservation.data.reservation.dto;

import com.sim.reservation.data.reservation.domain.Reservation;

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
	private Long reservationId;
	private String userId;
	private String name;
	private String phoneNum;
	private String email;
	private boolean isEmailReceiveDenied;
	private boolean isSnsReceiveDenied;

	@Builder
	public ReservationDto(Long reservationId,String userId, String name, String phoneNum, String email, boolean isEmailReceiveDenied,
		boolean isSnsReceiveDenied) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSnsReceiveDenied = isSnsReceiveDenied;
	}

	public static ReservationDto from(Reservation reservation) {
		return ReservationDto.builder()
			.reservationId(reservation.getId())
			.userId(reservation.getUserId())
			.name(reservation.getName())
			.phoneNum(reservation.getPhoneNum())
			.email(reservation.getEmail())
			.isSnsReceiveDenied(reservation.isSnsReceiveDenied())
			.isEmailReceiveDenied(reservation.isEmailReceiveDenied())
			.build();
	}
}
