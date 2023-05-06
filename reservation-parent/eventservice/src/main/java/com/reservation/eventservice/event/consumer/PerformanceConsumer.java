package com.reservation.eventservice.event.consumer;

import java.util.function.Consumer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.eventservice.event.PerformanceCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 공연 서비스 메시지 Consumer
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PerformanceConsumer {
	private final ApplicationEventPublisher publisher;

	@Bean
	Consumer<String> performanceCreatedEventConsumer() {
		return value -> {
			try {
				PerformanceCreatedEvent createdEvent = new ObjectMapper().readValue(value,
					PerformanceCreatedEvent.class);
				publisher.publishEvent(createdEvent);
			} catch (JsonProcessingException e) {
				log.error("Performance created Event Consumer error : {}", e.getMessage());
				e.printStackTrace();
			}
		};
	}
}
