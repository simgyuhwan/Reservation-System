package com.sim.reservation.data.reservation.event.consumer;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.EventResult;
import com.sim.reservation.data.reservation.event.payload.PerformanceEventPayload;
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
public class ReservationConsumer {
	private final ReservationEventService reservationEventService;

	/**
	 * 공연 생성 이벤트 Consumer
	 */
	@Bean
	public Function<DefaultEvent<PerformanceEventPayload>, EventResult> performanceCreatedConsumer() {
		return reservationEventService::savePerformanceInfo;
	}

	/**
	 * 공연 수정 이벤트 Consumer
	 */
	@Bean
	public Function<DefaultEvent<PerformanceEventPayload>, EventResult> performanceUpdatedConsumer() {
		return reservationEventService::updatePerformanceInfo;
	}

	/**
	 * 예약 신청 완료 이벤트 Consumer
	 */
	@Bean
	public Consumer<ReservationApplyCompleteEvent> reservationApplyComplete() {
		return reservationEventService::saveEvent;
	}

}
