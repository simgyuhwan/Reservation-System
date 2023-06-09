package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRefundCompleteEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {return reservationId;}

	public PaymentRefundCompleteEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}
}
