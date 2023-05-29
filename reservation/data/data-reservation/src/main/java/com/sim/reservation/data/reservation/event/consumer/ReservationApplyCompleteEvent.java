package com.sim.reservation.data.reservation.event.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationApplyCompleteEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public ReservationApplyCompleteEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

}
