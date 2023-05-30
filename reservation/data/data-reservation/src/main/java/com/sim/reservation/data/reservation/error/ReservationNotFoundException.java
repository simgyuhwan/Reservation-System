package com.sim.reservation.data.reservation.error;

import lombok.Getter;

/**
 * 예약 ID와 일치하는 정보 없음 예외
 */
@Getter
public class ReservationNotFoundException extends RuntimeException{
	private final Long id;

	public ReservationNotFoundException(ErrorMessage message, Long id) {
		super(message.getMessage() + id);
		this.id = id;
	}
}
