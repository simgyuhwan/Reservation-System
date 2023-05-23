package com.sim.performance.performancedomain.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.sim.performance.event.external.CreatedEventResultDto;
import com.sim.performance.event.external.UpdatedEventResultDto;
import com.sim.performance.event.core.payload.PerformanceCreatedPayload;
import com.sim.performance.event.core.payload.PerformanceUpdatedPayload;
import com.sim.performance.event.core.type.EventType;
import com.sim.performance.performancedomain.service.PerformanceEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalEventHandler {
	private final PerformanceEventService performanceEventService;

	/**
	 * 공연 생성 이벤트 저장
	 */
	@EventListener
	public void saveEvent(PerformanceCreatedPayload performanceCreatedPayload) {
		performanceEventService.saveEvent(performanceCreatedPayload, EventType.PERFORMANCE_CREATED);
	}

	/**
	 * 공연 생성 이벤트 결과 헨들링
	 */
	@EventListener
	public void handleResultEvent(CreatedEventResultDto createdEventResultDto) {
		performanceEventService.handlePerformanceCreatedEventResult(createdEventResultDto);
	}

	/**
	 * 공연 수정 이벤트 저장
	 */
	@EventListener
	public void saveEvent(PerformanceUpdatedPayload performanceUpdatedPayload) {
		performanceEventService.saveEvent(performanceUpdatedPayload, EventType.PERFORMANCE_UPDATE);
	}

	/**
	 * 공연 수정 이벤트 결과 헨들링
	 */
	@EventListener
	public void handleResultEvent(UpdatedEventResultDto updatedEventResultDto) {
		performanceEventService.handlePerformanceUpdatedEventResult(updatedEventResultDto);
	}

}
