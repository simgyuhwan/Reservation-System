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

	public static NotificationCompleteEvent from(NotificationRequestEvent notificationRequestEvent) {
		return new NotificationCompleteEvent(notificationRequestEvent.getId(), notificationRequestEvent.getReservationId());
	}
}
