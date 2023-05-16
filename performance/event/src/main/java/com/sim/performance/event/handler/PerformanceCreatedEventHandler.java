package com.sim.performance.performancedomain.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sim.performance.performancedomain.event.PerformanceEvent;
import com.sim.performance.performancedomain.event.producer.EventProducer;
import com.sim.performance.performancedomain.service.PerformanceEventService;
import com.sim.performance.performancedomain.type.EventStatusType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceCreatedEventHandler {
	private final EventProducer eventProducer;
	private final PerformanceEventService performanceEventService;

	@Async
	@TransactionalEventListener
	public void handleCreatedEvent(PerformanceEvent performanceEvent) {
		eventProducer.publishPerformanceCreatedEvent(performanceEvent);
	}

	@Transactional
	@EventListener
	public void handleEvent(PerformanceEvent performanceEvent) {
		performanceEventService.saveEvent(performanceEvent, EventStatusType.PENDING);
	}
}
