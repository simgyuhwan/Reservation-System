package com.reservation.common.type;

import lombok.Getter;

@Getter
public enum SourceType {
	MEMBER("member-service"),
	RESERVATION("reservation-service"),
	PERFORMANCE("performance-service"),
	NOTIFICATION("notification-service"),
	EVENT("event-service");

	private final String name;

	SourceType(String name) {
		this.name = name;
	}

}
