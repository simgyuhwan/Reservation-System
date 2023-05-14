package com.sim.performance.performancedomain.service;

import com.sim.performance.performancedomain.event.EventResult;
import com.sim.performance.performancedomain.event.PerformanceEvent;
import com.sim.performance.performancedomain.type.EventStatusType;

/**
 * 공연 이벤트 관리 서비스
 */
public interface PerformanceEventService {
	/**
	 * 이벤트 상태 저장
	 */
	void saveEvent(PerformanceEvent performanceEvent, EventStatusType status);

	/**
	 * 공연 생성 이벤트 처리
	 *
	 */
	void handlePerformanceCreatedEventResult(EventResult eventResult);
}
