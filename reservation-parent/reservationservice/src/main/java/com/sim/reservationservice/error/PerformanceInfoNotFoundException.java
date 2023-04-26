package com.sim.reservationservice.error;

/**
 * PerformanceInfoNotFoundException.java
 * 공연 정보 없음 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
public class PerformanceInfoNotFoundException extends RuntimeException {
	public PerformanceInfoNotFoundException() {
		super();
	}

	public PerformanceInfoNotFoundException(String message) {
		super(message);
	}
}
