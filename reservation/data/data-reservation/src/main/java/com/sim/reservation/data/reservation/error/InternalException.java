package com.sim.reservation.data.reservation.error;

/**
 * ReservationFailureException.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.15
 */
public class InternalException extends RuntimeException{
	public InternalException(Throwable cause) {
		super(cause);
	}
}
