package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundNotificationEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {return reservationId;}

	public RefundNotificationEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static RefundNotificationEvent from(PaymentRefundCompleteEvent paymentRefundCompleteEvent){
		return new RefundNotificationEvent(paymentRefundCompleteEvent.getId(),
			paymentRefundCompleteEvent.getReservationId());
	}
}
