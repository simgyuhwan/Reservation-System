package com.reservation.performanceservice.event.producer;

import com.reservation.common.event.payload.EventPayload;
import com.reservation.performanceservice.event.PerformanceEvent;

public interface PerformanceProducer {
	void publishCreatedEvent(PerformanceEvent<EventPayload> performanceEvent);
}
