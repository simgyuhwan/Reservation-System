package com.reservation.eventservice.event.producer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.reservation.common.event.DefaultEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPerformanceEventProducer implements PerformanceEventProducer {
	private final StreamBridge streamBridge;

	@Override
	public void publishCreatedEvent(DefaultEvent createdEvent) {
		log.info("Publishing Performance Created Event : {}", createdEvent);
		streamBridge.send("event-service.performance.created", createdEvent);
	}
}
