package com.sim.notification.event.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ReservationCancelRequest.java
 * 예약 취소 요청
 *
 * @author sgh
 * @since 2023.06.05
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {return reservationId;}

	public ReservationCancelEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}
}
