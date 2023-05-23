package com.sim.performance.performancedomain.service;

import com.sim.performance.event.external.CreatedEventResultDto;
import com.sim.performance.event.external.UpdatedEventResultDto;
import com.sim.performance.event.core.payload.Payload;
import com.sim.performance.event.core.type.EventType;

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

	/**
	 * 공연 수정 이벤트 재발행
	 */
	void rePublishPerformanceUpdateEvent();
}
