package com.sim.performance.performancedomain.event.producer;

import com.sim.performance.performancedomain.event.PerformanceEvent;

public interface EventProducer {
	void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent);
}
