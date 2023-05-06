package com.reservation.eventservice.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.reservation.eventservice.event.PerformanceCreatedEvent;
import com.reservation.eventservice.event.producer.PerformanceEventProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceEventHandler {
	private final PerformanceEventProducer performanceEventProducer;

	@EventListener
	public void handleCreatedEvent(PerformanceCreatedEvent createdEvent) {
		log.info("send notification..and....");
		performanceEventProducer.publishCreatedEvent(createdEvent);
	}
}
