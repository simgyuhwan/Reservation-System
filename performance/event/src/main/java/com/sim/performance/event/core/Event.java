package com.sim.performance.event.core;

import java.time.LocalDateTime;

import com.sim.performance.event.core.payload.Payload;
import com.sim.performance.event.core.type.EventStatusType;
import com.sim.performance.event.core.type.SourceType;

public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}
