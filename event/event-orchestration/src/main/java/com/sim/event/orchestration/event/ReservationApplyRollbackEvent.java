package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationApplyRollbackEvent {
	private String eventId;
	private Long reservationId;

	public String getId() {
		return this.eventId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	private ReservationApplyRollbackEvent(String id, Long reservationId) {
		this.eventId = id;
		this.reservationId = reservationId;
	}

	public static ReservationApplyRollbackEvent of(PaymentFailedEvent paymentFailedEvent) {
		return new ReservationApplyRollbackEvent(paymentFailedEvent.getId(), paymentFailedEvent.getReservationId());
	}
}
