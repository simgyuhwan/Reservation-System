package com.sim.reservation.data.reservation.domain;

/**
 * ReservationStatus.java
 * 예약 상태 enum 클래스
 *
 * @author sgh
 * @since 2023.04.27
 */
public enum ReservationStatus {
	RESERVED("예약 완료"),
	CANCELED("예약 취소"),
	COMPLETED("예약 종료");

	private final String status;

	ReservationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
