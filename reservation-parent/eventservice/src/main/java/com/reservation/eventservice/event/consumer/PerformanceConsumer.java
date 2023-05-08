package com.reservation.eventservice.event.consumer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.reservation.common.event.EventResult;
import com.reservation.common.event.PerformanceCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 공연 서비스 메시지 Consumer
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PerformanceConsumer {
	@Bean
	Function<Message<PerformanceCreatedEvent>, Message<PerformanceCreatedEvent>> performanceCreatedEventConsumer() {
		return event -> {
			log.info("Event ID : {}, publish a performance creation event", event.getHeaders().get("eventId"));
			return event;
		};
	}

	@Bean
	Function<EventResult, EventResult> performanceCreatedEventResult() {
		return event -> {
			log.info("Event ID : {}, performance registration event results : {}", event.getId(), event.isSuccess());
			return event;
		};
	}
}
