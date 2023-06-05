package com.sim.notification.event.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ReservationApplyEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public ReservationApplyEvent() {
	}

	private ReservationApplyEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

}
