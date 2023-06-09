package com.sim.event.orchestration.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.event.core.DefaultEvent;
import com.sim.event.core.Payload;
import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.PaymentRefundCompleteEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelEvent;
import com.sim.event.orchestration.event.ReservationCancelRequest;
import com.sim.event.orchestration.orchestrator.ReservationApplySaga;
import com.sim.event.orchestration.orchestrator.ReservationCancelSaga;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SagaConsumer {
	private final ReservationApplySaga reservationApplySaga;
	private final ReservationCancelSaga reservationCancelSaga;

	@Bean
	public Consumer<DefaultEvent<ReservationApplyRequest>> reservationApplyRequest() {
		return event -> {
			ReservationApplyRequest payload = (ReservationApplyRequest)event.getPayload();
			reservationApplySaga.start(payload);
		};
	}

	@Bean
	public Consumer<PaymentCompleteEvent> paymentComplete() {
		return reservationApplySaga::handle;
	}

	@Bean
	public Consumer<PaymentFailedEvent> paymentFailed() {
		return reservationApplySaga::handle;
	}

	@Bean
	public Consumer<NotificationCompleteEvent> notificationComplete() {
		return reservationApplySaga::handle;
	}

	@Bean
	public Consumer<ReservationCancelEvent> reservationCancelled() {
		return reservationApplySaga::handle;
	}

	@Bean
	public Consumer<DefaultEvent<ReservationCancelRequest>> reservationCancelRequest() {
		return event -> {
			Payload payload = event.getPayload();
			reservationCancelSaga.start(payload);
		};
	}

	@Bean
	public Consumer<PaymentRefundCompleteEvent> paymentRefundComplete(){
		return reservationCancelSaga::handle;
	}

	@Bean
	public Consumer<NotificationCompleteEvent> notificationCancelComplete() {
		return reservationCancelSaga::handle;
	}
}
