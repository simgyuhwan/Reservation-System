package com.reservation.performanceservice.types;

import lombok.Getter;

@Getter
public enum EventType {
	PERFORMANCE_CREATED("Performance Created Event");

	private final String name;

	EventType(String name) {
		this.name = name;
	}
}
