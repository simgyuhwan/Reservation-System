package com.sim.reservation.data.reservation.event;

import java.time.LocalDateTime;

import com.sim.reservation.data.reservation.event.payload.Payload;
import com.sim.reservation.data.reservation.type.EventStatusType;
import com.sim.reservation.data.reservation.type.SourceType;

public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}
