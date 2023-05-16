package com.sim.performance.performancedomain.event;

import java.time.LocalDateTime;

import com.sim.performance.performancedomain.type.EventStatusType;
import com.sim.performance.performancedomain.type.SourceType;
import com.sim.performance.performancedomain.event.payload.Payload;

public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}
