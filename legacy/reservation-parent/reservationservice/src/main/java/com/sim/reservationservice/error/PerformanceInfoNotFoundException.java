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
	private final Long id;

	public PerformanceInfoNotFoundException(ErrorMessage message, Long id) {
		super(message.name() + id);
		this.id= id;
	}
}
