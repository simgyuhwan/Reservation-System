package com.sim.performance.event.external;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.performance.event.core.EventResult;
import com.sim.performance.event.internal.InternalEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class PerformanceConsumer {
	private final InternalEventPublisher eventPublisher;

	@Bean
	public Consumer<EventResult> performanceCreatedResult() {
		return eventPublisher::publishPerformanceCreatedEventResult;
	}

	@Bean
	public Consumer<EventResult> performanceUpdatedResult() {
		return eventPublisher::publishPerformanceUpdatedEventResult;
	}
}
