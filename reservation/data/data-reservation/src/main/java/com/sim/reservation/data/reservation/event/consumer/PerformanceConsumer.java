package com.sim.reservation.data.reservation.event.consumer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.EventResult;
import com.sim.reservation.data.reservation.event.payload.PerformanceCreatedPayload;
import com.sim.reservation.data.reservation.service.ReservationEventService;

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
