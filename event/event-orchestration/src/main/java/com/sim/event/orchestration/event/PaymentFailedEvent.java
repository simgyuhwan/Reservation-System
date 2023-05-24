package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentFailedEvent {
	private String eventId;
	private Long reservationId;

	public String getId() {
		return this.eventId;
	}

	public Long getReservationId() {
		return reservationId;
	}
}
