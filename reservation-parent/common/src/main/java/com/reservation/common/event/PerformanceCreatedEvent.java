package com.reservation.common.event;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.reservation.common.event.payload.PerformanceCreatedPayload;

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
	private String status;
	private PerformanceCreatedPayload payload;
	private String eventType;
	private String source;

	@Builder
	public PerformanceCreatedEvent(String id, LocalDateTime eventDateTime, String status, String source,
		PerformanceCreatedPayload payload, String eventType) {
		this.id = id;
		this.eventDateTime = eventDateTime;
		this.source = source;
		this.payload = payload;
		this.eventType = eventType;
		this.status = status;
	}

	public Long getPerformanceId() {
		Assert.notNull(payload, "payload is must be not null");
		return payload.getPerformanceId();
	}

}
