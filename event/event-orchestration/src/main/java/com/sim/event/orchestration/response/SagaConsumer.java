package com.sim.event.orchestration.response;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelledEvent;
import com.sim.event.orchestration.orchestrator.ReservationApplySaga;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SagaConsumer {
	private final ReservationApplySaga reservationApplySaga;

	@Bean
	public Consumer<ReservationApplyRequest> reservationApplyRequest() {
		return reservationApplySaga::response;
	}

	@Bean
	public Consumer<PaymentCompleteEvent> paymentComplete() {
		return reservationApplySaga::response;
	}

	@Bean
	public Consumer<PaymentFailedEvent> paymentFailed() {
		return reservationApplySaga::response;
	}

	@Bean
	public Consumer<NotificationCompleteEvent> notificationComplete() {
		return reservationApplySaga::response;
	}

	@Bean
	public Consumer<ReservationCancelledEvent> reservationCancelled() {
		return reservationApplySaga::response;
	}
}
