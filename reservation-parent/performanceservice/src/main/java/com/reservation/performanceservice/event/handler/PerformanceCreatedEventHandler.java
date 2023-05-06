package com.reservation.performanceservice.event.handler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.reservation.performanceservice.event.PerformanceEvent;
import com.reservation.performanceservice.event.producer.PerformanceProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceCreatedEventHandler {
	private final PerformanceProducer performanceProducer;

	@Async("defaultExecutor")
	@TransactionalEventListener
	public void handleCreatedEvent(PerformanceEvent performanceEvent) {
		log.info("send event service....and..");
		performanceProducer.publishCreatedEvent(performanceEvent);
	}
}
