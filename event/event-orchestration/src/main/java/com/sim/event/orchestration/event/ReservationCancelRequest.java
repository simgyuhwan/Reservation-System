package com.sim.event.orchestration.event;

import com.sim.event.core.Payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelRequest implements Payload {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {return reservationId;}
}
