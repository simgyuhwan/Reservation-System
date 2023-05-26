package com.sim.event.core;

import java.time.LocalDateTime;

public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}
