package com.sim.performance.event.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sim.performance.event.core.PerformanceEvent;
import com.sim.performance.event.payload.PerformanceCreatedPayload;
import com.sim.performance.event.payload.PerformanceUpdatedPayload;
import com.sim.performance.event.publisher.ExternalEventPublisher;
import com.sim.performance.event.type.EventType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalEventHandler {
	private final ExternalEventPublisher externalEventPublisher;

	/**
	 * 공연 생성 이벤트 핸들러
	 *
	 * @param performanceCreatedPayload 공연 생성 이벤트 Payload
	 */
	@Async
	@TransactionalEventListener
	public void handleCreatedEvent(PerformanceCreatedPayload performanceCreatedPayload) {
		PerformanceEvent performanceEvent = PerformanceEvent.pending(EventType.PERFORMANCE_CREATED, performanceCreatedPayload);
		externalEventPublisher.publishPerformanceCreatedEvent(performanceEvent);
	}

	/**
	 * 공연 수정 이벤트 핸들러
	 *
	 * @param performanceUpdatedPayload 공연 수정 이벤트 Paylaod
	 */
	@Async
	@TransactionalEventListener
	public void handleUpdatedEvent(PerformanceUpdatedPayload performanceUpdatedPayload) {
		PerformanceEvent performanceEvent = PerformanceEvent.pending(EventType.PERFORMANCE_UPDATE, performanceUpdatedPayload);
		externalEventPublisher.publishPerformanceUpdatedEvent(performanceEvent);
	}
}
