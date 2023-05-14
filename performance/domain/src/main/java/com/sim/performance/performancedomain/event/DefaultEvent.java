package com.sim.performance.performancedomain.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sim.performance.performancedomain.type.EventStatusType;
import com.sim.performance.performancedomain.type.SourceType;
import com.sim.performance.performancedomain.event.payload.Payload;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultEvent<T extends Payload> implements Event{
	private String id;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime eventDateTime;
	private EventStatusType status;
	private T payload;
	private String eventType;
	private SourceType source;

	@Builder
	public DefaultEvent(String id, LocalDateTime eventDateTime, EventStatusType status, SourceType source,
		T payload, String eventType) {
		this.id = id;
		this.eventDateTime = eventDateTime;
		this.source = source;
		this.payload = payload;
		this.eventType = eventType;
		this.status = status;
	}

	@Override
	public String getId() {
		return id;
	}

	public String eventType() {
		return eventType;
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
}
