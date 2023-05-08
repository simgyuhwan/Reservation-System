package com.reservation.performanceservice.types;

/**
 * PayloadType.java
 * 이벤트 페이로드
 *
 * @author sgh
 * @since 2023.05.08
 */
public enum PayloadType {
	PERFORMANCE_CREATED_PAYLOAD("Performance Created Event Payload");

	private final String name;

	PayloadType(String name) {
		this.name = name;
	}
}
