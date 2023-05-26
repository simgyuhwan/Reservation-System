package com.sim.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRequestEvent {
	private String id;
	private Long reservationId;

	public PaymentRequestEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}
}
