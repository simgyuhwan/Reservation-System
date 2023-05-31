package com.sim.reservation.data.reservation.type;

import lombok.Getter;

@Getter
public enum ReservationStatusType {
	PAYMENT_PENDING("결제 대기"),
	PAYMENT_COMPLETED("결제 완료"),
	CANCELLATION_REQUESTED("취소 신청"),
	CANCELLATION_COMPLETED("취소 완료");

	private final String status;

	ReservationStatusType(String status) {
		this.status = status;
	}
}
