package com.reservation.performanceservice.event;

import com.reservation.common.event.Event;
import com.reservation.common.event.payload.EventPayload;
import com.reservation.common.type.EventStatusTypes;
import com.reservation.common.type.SourceType;
import com.reservation.performanceservice.type.EventType;

import lombok.Getter;

@Getter
public class PerformanceEvent<T extends EventPayload> extends Event {
	private final T payload;
	private final String type;

	private PerformanceEvent(EventType type, T payload) {
		super(SourceType.PERFORMANCE, EventStatusTypes.PENDING);
		this.type = type.name();
		this.payload = payload;
	}

	public static <T extends EventPayload> PerformanceEvent from(EventType eventType, T payload) {
		return new PerformanceEvent(eventType, payload);
	}

}
