package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRefundEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {return reservationId;}

	public PaymentRefundEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static PaymentRefundEvent from(ReservationCancelRequest reservationCancelRequest) {
		return new PaymentRefundEvent(reservationCancelRequest.getId(), reservationCancelRequest.getReservationId());
	}
}
