package com.sim.performance.event.publisher;

import com.sim.performance.event.core.PerformanceEvent;

/**
 * 외부 서비스 이벤트 발행 클래스
 */
public interface ExternalEventPublisher {
	void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent);

	void publishPerformanceUpdatedEvent(PerformanceEvent performanceEvent);
}
