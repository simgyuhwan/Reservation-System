package com.reservation.performanceservice.event.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reservation.common.event.EventResult;
import com.reservation.performanceservice.application.PerformanceEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class PerformanceConsumer {
	private final PerformanceEventService performanceEventService;

	@Bean
	public Consumer<EventResult> performanceCreatedResult() {
		return performanceEventService::eventUpdate;
	}

}
