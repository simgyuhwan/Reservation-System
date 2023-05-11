package com.reservation.performanceservice.application;

import com.reservation.common.event.EventResult;
import com.reservation.common.types.EventStatusType;
import com.reservation.performanceservice.event.PerformanceEvent;

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
