package com.sim.event.orchestration.event;

import com.sim.event.core.Payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelEvent implements Payload {
	private String eventId;
	private Long reservationId;

	public String getId() {
		return this.eventId;
	}

	public Long getReservationId() {
		return reservationId;
	}
}
