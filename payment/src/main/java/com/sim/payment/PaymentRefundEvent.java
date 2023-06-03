package com.sim.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRefundEvent {
	private String id;
	private Long reservationId;

	public PaymentRefundEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}
}
