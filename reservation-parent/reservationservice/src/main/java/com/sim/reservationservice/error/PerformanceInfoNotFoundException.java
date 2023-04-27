package com.sim.reservationservice.error;

import com.reservation.common.error.ErrorMessage;

import lombok.Getter;

/**
 * PerformanceInfoNotFoundException.java
 * 공연 정보 없음 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
public class PerformanceInfoNotFoundException extends RuntimeException {
	private Long performanceId;

	public PerformanceInfoNotFoundException() {
		super();
	}

	public PerformanceInfoNotFoundException(String message) {
		super(message);
	}

	public PerformanceInfoNotFoundException(ErrorMessage message, Long performanceId) {
		super(message.name() + performanceId);
		this.performanceId = performanceId;
	}
}
