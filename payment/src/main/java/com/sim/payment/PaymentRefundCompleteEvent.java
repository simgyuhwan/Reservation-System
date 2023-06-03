package com.sim.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRefundCompleteEvent {
	private String id;
	private Long reservationId;

	public PaymentRefundCompleteEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static PaymentRefundCompleteEvent from(PaymentRefundEvent paymentRefundEvent) {
		return new PaymentRefundCompleteEvent(paymentRefundEvent.getId(), paymentRefundEvent.getReservationId());
	}
}
