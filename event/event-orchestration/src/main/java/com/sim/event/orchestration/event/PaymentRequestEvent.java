package com.sim.event.orchestration.event;

public class PaymentRequestEvent {
	private String id;
	private Long reservationId;

	private PaymentRequestEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static PaymentRequestEvent from(ReservationApplyRequest request) {
		return new PaymentRequestEvent(request.getId(), request.getReservationId());
	}
}
