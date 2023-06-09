package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelCompleteEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public ReservationCancelCompleteEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static ReservationCancelCompleteEvent from(NotificationCompleteEvent notificationCompleteEvent) {
		return new ReservationCancelCompleteEvent(notificationCompleteEvent.getId(),
			notificationCompleteEvent.getReservationId());
	}
}
