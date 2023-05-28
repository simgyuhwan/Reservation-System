package com.sim.notification.email.message;

import lombok.Builder;

public class ReservationCompleteMessage {
	private String username;
	private String performanceName;
	private String startDate;
	private String startTime;
	private String place;

	public ReservationCompleteMessage() {
	}

	@Builder
	public ReservationCompleteMessage(String username, String performanceName, String startDate, String startTime, String place) {
		this.username = username;
		this.performanceName = performanceName;
		this.startDate = startDate;
		this.startTime = startTime;
		this.place = place;
	}

	public String getUsername() {
		return username;
	}

	public String getPerformanceName() {
		return performanceName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getPlace() {
		return place;
	}
}
