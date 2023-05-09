package com.reservation.performanceservice.event;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import com.reservation.common.event.Event;
import com.reservation.common.event.payload.Payload;
import com.reservation.common.types.EventStatusType;
import com.reservation.common.types.SourceType;
import com.reservation.performanceservice.types.EventType;

import lombok.ToString;

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
	public String getSource() {
		return source.name();
	}

	@Override
	public String getStatus() {
		return status.name();
	}

	@Override
	public LocalDateTime getEventDateTime() {
		return eventDateTime;
	}

	private PerformanceEvent(Builder builder) {
		this.id = builder.id;
		this.eventDateTime = builder.eventDateTime;
		this.status = builder.status;
		this.payload = builder.payload;
		this.eventType = builder.eventType;
		this.source = builder.source;
	}

	public EventType getEventType() {
		return eventType;
	}

	public static Builder pending(EventType eventType) {
		return new Builder()
			.id(UUID.randomUUID().toString())
			.eventDateTime(LocalDateTime.now())
			.source(SourceType.PERFORMANCE_SERVICE)
			.eventType(eventType)
			.status(EventStatusType.PENDING);
	}

	public static class Builder {
		private String id;
		private LocalDateTime eventDateTime;
		private EventStatusType status;
		private SourceType source;
		private Payload payload;
		private EventType eventType;

		private Builder(){}

		private Builder id(String id) {
			this.id = id;
			return this;
		}

		private Builder eventDateTime(LocalDateTime eventDateTime) {
			this.eventDateTime = eventDateTime;
			return this;
		}

		private Builder status(EventStatusType status) {
			this.status = status;
			return this;
		}

		private Builder eventType(EventType eventType) {
			this.eventType = eventType;
			return this;
		}

		private Builder source(SourceType source) {
			this.source = source;
			return this;
		}
		
		public PerformanceEvent payload(Supplier<Payload> supplier) {
			this.payload = supplier.get();
			return new PerformanceEvent(this);
		}
	}
}
