package com.sim.event.orchestration.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.event.core.DefaultEvent;
import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelEvent;
import com.sim.event.orchestration.orchestrator.ReservationRequestSaga;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SagaConsumer {
	private final ReservationRequestSaga reservationRequestSaga;

	@Bean
	public Consumer<DefaultEvent<ReservationApplyRequest>> reservationApplyRequest() {
		return a -> {
			ReservationApplyRequest request = (ReservationApplyRequest)a.getPayload();
			reservationRequestSaga.start(request);
		};
	}

	@Bean
	public Consumer<PaymentCompleteEvent> paymentComplete() {
		return reservationRequestSaga::handle;
	}

	@Bean
	public Consumer<PaymentFailedEvent> paymentFailed() {
		return reservationRequestSaga::handle;
	}

	@Bean
	public Consumer<NotificationCompleteEvent> notificationComplete() {
		return reservationRequestSaga::handle;
	}

	@Bean
	public Consumer<ReservationCancelEvent> reservationCancelled() {
		return reservationRequestSaga::handle;
	}
}
