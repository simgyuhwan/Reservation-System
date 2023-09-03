package com.sim.reservation.data.reservation.dto;

import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;

import com.sim.reservation.data.reservation.type.ReservationStatusType;
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
	private boolean isSmsReceiveDenied;

	@Builder
	public ReservationDto(Long reservationId,String userId, String name, String phoneNum, String email, boolean isEmailReceiveDenied,
		boolean isSmsReceiveDenied) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSmsReceiveDenied = isSmsReceiveDenied;
	}

	public static ReservationDto from(Reservation reservation) {
		return ReservationDto.builder()
			.reservationId(reservation.getId())
			.userId(reservation.getUserId())
			.name(reservation.getName())
			.phoneNum(reservation.getPhoneNum())
			.email(reservation.getEmail())
			.isSmsReceiveDenied(reservation.isSmsReceiveDenied())
			.isEmailReceiveDenied(reservation.isEmailReceiveDenied())
			.build();
	}

	public Reservation toEntity(PerformanceSchedule performanceSchedule) {
		return Reservation.builder()
			.userId(userId)
			.name(name)
			.email(email)
			.phoneNum(phoneNum)
			.isEmailReceiveDenied(isEmailReceiveDenied())
			.isSmsReceiveDenied(isSmsReceiveDenied)
			.performanceSchedule(performanceSchedule)
			.status(ReservationStatusType.PAYMENT_PENDING)
			.build();
	}
}
