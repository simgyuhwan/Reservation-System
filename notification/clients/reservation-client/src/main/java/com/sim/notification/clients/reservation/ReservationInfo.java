package com.sim.notification.clients.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.Builder;
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

	@Builder
	private ReservationInfo(Long id, String username, String phoneNum, String email,
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

	public boolean canSendEmail() {
		return !isEmailReceiveDenied;
	}

	public boolean canSendSns() {
		return !isSnsReceiveDenied;
	}

	public String getStartDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		return startDate.format(dateFormatter);
	}

	public String getStartTime() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		return startTime.format(timeFormatter);
	}
}
