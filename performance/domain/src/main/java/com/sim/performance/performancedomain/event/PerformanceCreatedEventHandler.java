package com.sim.performance.performancedomain.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.sim.performance.event.dto.CreatedEventResultDto;
import com.sim.performance.event.payload.PerformanceCreatedPayload;
import com.sim.performance.performancedomain.service.PerformanceEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceCreatedEventHandler {
	private final PerformanceEventService performanceEventService;

	@EventListener
	public void saveEvent(PerformanceCreatedPayload performanceCreatedPayload) {
		performanceEventService.savePerformanceCreatedEvent(performanceCreatedPayload);
	}

	@EventListener
	public void handleResultEvent(CreatedEventResultDto createdEventResultDto) {
		performanceEventService.handlePerformanceCreatedEventResult(createdEventResultDto);
	}

}
