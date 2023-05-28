package com.sim.reservation.boot.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sim.reservation.data.reservation.dto.ReservationInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInfoResponse {
	private Long id;
	private String username;
	private String phoneNum;
	private String email;
	private String performanceName;
	private LocalDate startDate;
	private LocalTime startTime;
	private String place;
	private boolean isEmailReceiveDenied;
	private boolean isSnsReceiveDenied;

	@Builder
	private ReservationInfoResponse(Long id, String username, String phoneNum, String email,
		String performanceName, LocalDate startDate, LocalTime startTime, String place, boolean isEmailReceiveDenied,
		boolean isSnsReceiveDenied) {
		this.id = id;
		this.username = username;
		this.phoneNum = phoneNum;
		this.email = email;
		this.performanceName = performanceName;
		this.startDate = startDate;
		this.startTime = startTime;
		this.place = place;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSnsReceiveDenied = isSnsReceiveDenied;
	}

	public static ReservationInfoResponse from(ReservationInfo reservationInfo) {
		return ReservationInfoResponse.builder()
			.id(reservationInfo.getId())
			.username(reservationInfo.getUsername())
			.phoneNum(reservationInfo.getPhoneNum())
			.email(reservationInfo.getEmail())
			.performanceName(reservationInfo.getPerformanceName())
			.startDate(reservationInfo.getStartDate())
			.startTime(reservationInfo.getStartTime())
			.place(reservationInfo.getPlace())
			.isEmailReceiveDenied(reservationInfo.isEmailReceiveDenied())
			.isSnsReceiveDenied(reservationInfo.isSnsReceiveDenied())
			.build();
	}
}
