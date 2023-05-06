package com.reservation.eventservice.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.reservation.eventservice.event.payload.PerformanceCreatedPayload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformanceCreatedEvent {
	private String id;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime eventDateTime;
	private String source;
	private PerformanceCreatedPayload payload;
	private String type;
	private String status;

	@Builder
	public PerformanceCreatedEvent(String id, LocalDateTime eventDateTime, String source,
		PerformanceCreatedPayload payload, String type, String status) {
		this.id = id;
		this.eventDateTime = eventDateTime;
		this.source = source;
		this.payload = payload;
		this.type = type;
		this.status = status;
	}
}
