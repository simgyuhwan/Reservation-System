package com.sim.performance.performancedomain.event.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.performance.performancedomain.event.EventResult;
import com.sim.performance.performancedomain.service.PerformanceEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class PerformanceConsumer {
	private final PerformanceEventService performanceEventService;

	@Bean
	public Consumer<EventResult> performanceCreatedResult() {
		return performanceEventService::handlePerformanceCreatedEventResult;
	}

}
