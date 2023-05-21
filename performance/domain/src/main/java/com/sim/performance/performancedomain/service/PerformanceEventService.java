package com.sim.performance.performancedomain.service;

import com.sim.performance.event.dto.CreatedEventResultDto;
import com.sim.performance.event.dto.UpdatedEventResultDto;
import com.sim.performance.event.payload.Payload;
import com.sim.performance.event.type.EventType;

/**
 * 공연 이벤트 관리 서비스
 */
public interface PerformanceEventService {
	/**
	 * 공연 이벤트 상태 저장
	 */
	void saveEvent(Payload payload, EventType eventType);

	/**
	 * 공연 생성 이벤트 처리
	 */
	void handlePerformanceCreatedEventResult(CreatedEventResultDto createdEventResultDto);

	/**
	 * 공연 수정 이벤트 처리
	 */
	void handlePerformanceUpdatedEventResult(UpdatedEventResultDto updatedEventResultDto);
}
