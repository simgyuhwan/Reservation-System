package com.sim.reservationservice.error;

import com.reservation.common.error.ErrorMessage;

import lombok.Getter;

/**
 * ReservationNotPossibleException.java
 * 예약 불가능 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
public class ReservationNotPossibleException extends RuntimeException {
	private Long performanceId;

	public ReservationNotPossibleException() {
		super();
	}

	public ReservationNotPossibleException(String message) {
		super(message);
	}

	public ReservationNotPossibleException(ErrorMessage errorMessage, Long performanceId) {
		super(errorMessage.name() + performanceId);
		this.performanceId = performanceId;
	}
}
