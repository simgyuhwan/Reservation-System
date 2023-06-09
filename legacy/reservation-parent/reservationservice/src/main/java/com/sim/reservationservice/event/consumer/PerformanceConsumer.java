package com.sim.reservationservice.event.consumer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reservation.common.event.DefaultEvent;
import com.reservation.common.event.EventResult;
import com.reservation.common.event.payload.PerformanceCreatedPayload;
import com.sim.reservationservice.application.ReservationEventService;

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
	private final ReservationEventService reservationEventService;

	@Bean
	public Function<DefaultEvent<PerformanceCreatedPayload>, EventResult> performanceCreatedConsumer() {
		return reservationEventService::savePerformanceInfo;
	}

}
