package com.sim.notification.event.consumer;

public class NotificationRequestEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public NotificationRequestEvent() {
	}

	private NotificationRequestEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

}
