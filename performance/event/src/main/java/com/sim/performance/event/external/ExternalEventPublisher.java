package com.sim.performance.event.external;

import com.sim.performance.event.core.PerformanceEvent;

/**
 * 외부 서비스 이벤트 발행 클래스
 */
public interface ExternalEventPublisher {

	/**
	 * 공연 생성 이벤트 발행
	 */
	void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent);

	/**
	 * 공연 수정 이벤트 발행
	 */
	void publishPerformanceUpdatedEvent(PerformanceEvent performanceEvent);
}
