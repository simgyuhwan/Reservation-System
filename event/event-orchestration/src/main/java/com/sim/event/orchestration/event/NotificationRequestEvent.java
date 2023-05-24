package com.sim.event.orchestration.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationRequestEvent {
	private String id;
	private Long reservationId;

	public String getId() {
		return id;
	}

	public Long getReservationId() {
		return reservationId;
	}

	private NotificationRequestEvent(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static NotificationRequestEvent from(PaymentCompleteEvent event) {
		return new NotificationRequestEvent(event.getId(), event.getReservationId());
	}
}
