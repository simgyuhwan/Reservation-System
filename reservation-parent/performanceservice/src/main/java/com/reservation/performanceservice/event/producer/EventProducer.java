package com.reservation.performanceservice.event.producer;

import com.reservation.performanceservice.event.PerformanceEvent;

public interface EventProducer {
	void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent);
}
