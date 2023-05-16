package com.sim.performance.performancedomain.service;

import com.sim.performance.event.dto.CreatedEventResultDto;
import com.sim.performance.event.payload.PerformanceCreatedPayload;

/**
 * 공연 이벤트 관리 서비스
 */
public interface PerformanceEventService {
	/**
	 * 이벤트 상태 저장
	 */
	void savePerformanceCreatedEvent(PerformanceCreatedPayload performanceCreatedPayload);

	/**
	 * 공연 생성 이벤트 처리
	 *
	 */
	void handlePerformanceCreatedEventResult(CreatedEventResultDto createdEventResultDto);
}
