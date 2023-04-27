package com.sim.reservationservice.domain;

/**
 * ReservationStatus.java
 * 예약 상태 enum 클래스
 *
 * @author sgh
 * @since 2023.04.27
 */
public enum ReservationStatus {
	RESERVED("예약됨"),
	CANCELED("취소됨"),
	COMPLETED("완료됨");

	private final String status;

	ReservationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
