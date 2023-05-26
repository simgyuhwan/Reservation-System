package com.sim.event.orchestration.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.event.core.DefaultEvent;
import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelledEvent;
import com.sim.event.orchestration.orchestrator.Saga;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SagaConsumer {
	private final Saga saga;

	@Bean
	public Consumer<DefaultEvent<ReservationApplyRequest>> reservationApplyRequest() {
		return a -> {
			ReservationApplyRequest request = (ReservationApplyRequest)a.getPayload();
			saga.startSaga(request);
		};
	}

	@Bean
	public Consumer<PaymentCompleteEvent> paymentComplete() {
		return a -> {
			System.out.println(a.getId());
			saga.response(a);
		};
	}

	@Bean
	public Consumer<PaymentFailedEvent> paymentFailed() {
		return saga::response;
	}

	@Bean
	public Consumer<NotificationCompleteEvent> notificationComplete() {
		return saga::response;
	}

	@Bean
	public Consumer<ReservationCancelledEvent> reservationCancelled() {
		return saga::response;
	}
}
