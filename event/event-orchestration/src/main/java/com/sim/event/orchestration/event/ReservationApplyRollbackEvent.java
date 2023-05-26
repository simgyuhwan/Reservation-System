package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationApplyRollbackEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	private ReservationApplyRollbackEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static ReservationApplyRollbackEvent of(PaymentFailedEvent paymentFailedEvent) {
		return new ReservationApplyRollbackEvent(paymentFailedEvent.getId(), paymentFailedEvent.getReservationId());
	}
}
