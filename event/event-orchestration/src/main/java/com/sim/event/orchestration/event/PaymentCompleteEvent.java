package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentCompleteEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return this.id;
	}

	public Long getReservationId() {
		return reservationId;
	}
}
