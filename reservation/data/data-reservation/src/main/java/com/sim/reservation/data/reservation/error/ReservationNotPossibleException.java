package com.sim.reservation.data.reservation.error;

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
	private final Long id;

	public ReservationNotPossibleException(ErrorMessage errorMessage, Long id) {
		super(errorMessage.name() + id);
		this.id = id;
	}
}
