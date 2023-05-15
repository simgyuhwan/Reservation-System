package com.sim.reservation.data.reservation.error;

import lombok.Getter;

/**
 * SoldOutException.java
 * 공연 매진 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
public class SoldOutException extends RuntimeException {
	private Long performanceScheduleId;

	public SoldOutException() {
		super();
	}

	public SoldOutException(String message) {
		super(message);
	}

	public SoldOutException(ErrorMessage errorMessage, Long performanceScheduleId) {
		super(errorMessage.name() + performanceScheduleId);
		this.performanceScheduleId = performanceScheduleId;
	}
}
