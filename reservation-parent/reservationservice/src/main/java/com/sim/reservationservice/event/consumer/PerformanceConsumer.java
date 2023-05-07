package com.sim.reservationservice.event.consumer;

import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reservation.common.error.ErrorMessage;
import com.reservation.common.event.EventResult;
import com.reservation.common.event.PerformanceCreatedEvent;
import com.sim.reservationservice.application.PerformanceSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceConsumer.java
 * 공연 구독 클래스
 *
 * @author sgh
 * @since 2023.04.18
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PerformanceConsumer {
	private final PerformanceSyncService performanceSyncService;
	private final StreamBridge streamBridge;

	@Bean
	public Consumer<PerformanceCreatedEvent> performanceCreatedConsumer() {
		return event -> {
			Long performanceId = event.getPerformanceId();
			boolean result = performanceSyncService.requestAndSavePerformanceInfo(performanceId);
			sendEventResult(event.getId(), result);
		};
	}

	private void sendEventResult(String eventId, boolean result) {
		EventResult eventResult = createEventResult(eventId, result);
		streamBridge.send("reservation-service.performance.created.result", eventResult);
	}

	private EventResult createEventResult(String eventId, boolean result) {
		if(result) {
			return EventResult.success(eventId);
		}
		else {
			return EventResult.fail(eventId, ErrorMessage.FAILED_TO_SEARCH_PERFORMANCE_IN_RESERVATION_SERVICE.name());
		}
	}
}
