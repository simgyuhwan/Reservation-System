package com.sim.notification.event.consumer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationCompleteEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public NotificationCompleteEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static NotificationCompleteEvent from(ReservationApplyEvent reservationApplyEvent) {
		return new NotificationCompleteEvent(reservationApplyEvent.getId(), reservationApplyEvent.getReservationId());
	}

	public static NotificationCompleteEvent from(ReservationCancelEvent reservationCancelEvent) {
		return new NotificationCompleteEvent(reservationCancelEvent.getId(), reservationCancelEvent.getReservationId());
	}
}
