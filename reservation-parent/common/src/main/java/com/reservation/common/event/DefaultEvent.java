package com.reservation.common.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.reservation.common.event.payload.Payload;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultEvent<T extends Payload> implements Event{
	private String id;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime eventDateTime;
	private String status;
	private T payload;
	private String eventType;
	private String source;

	@Builder
	public DefaultEvent(String id, LocalDateTime eventDateTime, String status, String source,
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
	public String getSource() {
		return source;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}
}
