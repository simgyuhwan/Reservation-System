package com.sim.performance.event.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sim.performance.event.core.PerformanceEvent;
import com.sim.performance.event.payload.PerformanceCreatedPayload;
import com.sim.performance.event.publisher.ExternalEventPublisher;
import com.sim.performance.event.type.EventType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceEventHandler {
	private final ExternalEventPublisher externalEventPublisher;

	@Async
	@TransactionalEventListener
	public void passToExternalService(PerformanceCreatedPayload performanceCreatedPayload) {
		PerformanceEvent performanceEvent = PerformanceEvent.pending(EventType.PERFORMANCE_CREATED, performanceCreatedPayload);
		externalEventPublisher.publishPerformanceCreatedEvent(performanceEvent);
	}

}
