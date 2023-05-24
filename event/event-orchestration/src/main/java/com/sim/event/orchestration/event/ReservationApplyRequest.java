package com.sim.event.orchestration.event;

public class ReservationApplyRequest {
	private String eventId;
	private Long reservationId;

	public String getId() {
		return eventId;
	}

	public Long getReservationId() {return reservationId;}
}
