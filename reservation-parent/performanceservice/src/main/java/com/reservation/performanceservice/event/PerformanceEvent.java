package com.reservation.performanceservice.event;

import java.time.LocalDateTime;

import com.reservation.common.event.Event;
import com.reservation.common.event.payload.Payload;
import com.reservation.common.types.EventStatusType;
import com.reservation.common.types.SourceType;
import com.reservation.performanceservice.types.EventType;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PerformanceEvent implements Event {
	private final String id;
	private final LocalDateTime eventDateTime;
	private final EventStatusType status;
	private final SourceType source;
	private final Payload payload;
	private final EventType eventType;

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

	@Builder
	private PerformanceEvent(String id, LocalDateTime eventDateTime, EventStatusType status,
		SourceType source, Payload payload, EventType eventType) {
		this.id = id;
		this.eventDateTime = eventDateTime;
		this.status = status;
		this.source = source;
		this.payload = payload;
		this.eventType = eventType;
	}

	public static PerformanceEvent of(String id, LocalDateTime eventDateTime, EventStatusType status,
		SourceType source, Payload payload, EventType eventType) {
		return PerformanceEvent.builder()
			.id(id)
			.eventDateTime(eventDateTime)
			.status(status)
			.source(source)
			.payload(payload)
			.eventType(eventType)
			.build();
	}
}
