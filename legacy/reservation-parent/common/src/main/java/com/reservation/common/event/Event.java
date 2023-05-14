package com.reservation.common.event;

import java.time.LocalDateTime;

import com.reservation.common.event.payload.Payload;
import com.reservation.common.types.EventStatusType;
import com.reservation.common.types.SourceType;

public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}
