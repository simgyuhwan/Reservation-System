package com.reservation.performanceservice.type;

import lombok.Getter;

@Getter
public enum EventType {
	PERFORMANCE_CREATED("Performance Created Event");

	private final String name;

	EventType(String name) {
		this.name = name;
	}
}
