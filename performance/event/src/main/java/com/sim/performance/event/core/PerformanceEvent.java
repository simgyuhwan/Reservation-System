package com.sim.performance.event.core;

import java.time.LocalDateTime;

import com.sim.performance.event.core.payload.Payload;
import com.sim.performance.event.core.type.EventStatusType;
import com.sim.performance.event.core.type.EventType;
import com.sim.performance.event.core.type.SourceType;

import lombok.Builder;

public record PerformanceEvent(String id, LocalDateTime eventDateTime,
							   EventStatusType status,
							   SourceType source,
							   Payload payload,
							   EventType eventType) implements Event {
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Payload getPayload() {
		return payload;
	}

	@Override
	public SourceType getSource() {
		return source;
	}

	@Override
	public EventStatusType getStatus() {
		return status;
	}

	@Override
	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}

	public EventType getEventType() {
		return eventType;
	}

	@Builder
	public PerformanceEvent {
	}

	public static PerformanceEvent pending(EventType eventType, Payload payload) {
		return PerformanceEvent.builder()
			.id(payload.getId())
			.eventDateTime(LocalDateTime.now())
			.source(SourceType.PERFORMANCE_SERVICE)
			.eventType(eventType)
			.status(EventStatusType.PENDING)
			.payload(payload)
			.build();
	}

}
