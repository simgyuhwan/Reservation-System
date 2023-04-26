package com.sim.reservationservice.error;

/**
 * ReservationNotPossibleException.java
 * 예약 불가능 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
public class ReservationNotPossibleException extends RuntimeException {
	public ReservationNotPossibleException() {
		super();
	}

	public ReservationNotPossibleException(String message) {
		super(message);
	}
}
