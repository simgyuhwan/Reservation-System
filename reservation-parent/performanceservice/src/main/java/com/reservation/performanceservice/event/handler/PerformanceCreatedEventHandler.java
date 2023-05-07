package com.reservation.performanceservice.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.reservation.common.event.payload.EventPayload;
import com.reservation.common.type.EventStatusTypes;
import com.reservation.performanceservice.application.PerformanceEventService;
import com.reservation.performanceservice.event.PerformanceEvent;
import com.reservation.performanceservice.event.producer.PerformanceProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceCreatedEventHandler {
	private final PerformanceProducer performanceProducer;
	private final PerformanceEventService performanceEventService;

	@Async("defaultExecutor")
	@TransactionalEventListener
	public void handleCreatedEvent(PerformanceEvent<EventPayload> performanceEvent) {
		performanceProducer.publishCreatedEvent(performanceEvent);
	}

	@Transactional
	@EventListener
	public void handleEvent(PerformanceEvent<EventPayload> performanceEvent) {
		performanceEventService.saveEvent(performanceEvent, EventStatusTypes.PENDING);
	}
}
