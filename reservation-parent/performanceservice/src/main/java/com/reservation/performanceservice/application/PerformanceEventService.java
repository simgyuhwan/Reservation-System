package com.reservation.performanceservice.application;

import com.reservation.common.event.EventResult;
import com.reservation.common.event.payload.EventPayload;
import com.reservation.common.type.EventStatusTypes;
import com.reservation.performanceservice.event.PerformanceEvent;

/**
 * 공연 이벤트 관리 서비스
 */
public interface PerformanceEventService {
	/**
	 * 이벤트 상태 저장
	 */
	void saveEvent(PerformanceEvent<EventPayload> performanceEvent, EventStatusTypes status);

	/**
	 * 이벤트 상태 업데이트
	 *
	 */
	void eventUpdate(EventResult eventResult);
}
