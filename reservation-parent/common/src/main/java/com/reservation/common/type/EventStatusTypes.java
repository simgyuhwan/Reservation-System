package com.reservation.common.type;

public enum EventStatusTypes {
	PENDING("pending"),
	FAIL("fail"),
	SUCCESS("success");

	private final String name;

	EventStatusTypes(String name) {
		this.name = name;
	}
}
