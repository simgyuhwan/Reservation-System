package com.reservation.performanceservice.event.producer;

import com.reservation.performanceservice.event.PerformanceEvent;

public interface PerformanceProducer {
	void publishCreatedEvent(PerformanceEvent performanceEvent);
}
