package com.sim.reservation.data.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.IrrevocableException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInfo {
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

	public ReservationInfo(Long id, String username, String phoneNum, String email, String performanceName,
		LocalDate startDate, LocalTime startTime, String place, boolean isEmailReceiveDenied,
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

	public void validateCancellationDate() {
		LocalDate today = LocalDate.now();
		if(today.isEqual(startDate) || today.isAfter(startDate)) {
			throw new IrrevocableException(ErrorMessage.RESERVATION_CANCEL_NOT_POSSIBLE_DATE, id);
		}
	}
}
