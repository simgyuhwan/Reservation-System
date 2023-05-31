package com.sim.event.orchestration.orchestrator;

import org.springframework.stereotype.Service;

import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelEvent;
import com.sim.event.orchestration.event.ReservationCancelRequest;
import com.sim.event.orchestration.publish.ExternalEventPublisher;
import com.sim.event.store.domain.SagaState;
import com.sim.event.store.service.SagaStateService;
import com.sim.event.store.type.SagaStep;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * 예약 취소 오케스트레이터
 */
@Service
@RequiredArgsConstructor
public class ReservationCancelSaga implements Saga{
	private final SagaStateService sagaStateService;
	private final ExternalEventPublisher eventPublisher;
	private EventBus eventBus;

	private static final SagaStep START = SagaStep.START;
	private static final SagaStep PAYMENT_REFUND_REQUEST = SagaStep.PAYMENT_REFUND_REQUEST;
	private static final SagaStep PAYMENT_COMPLETE = SagaStep.PAYMENT_COMPLETE;
	private static final SagaStep PAYMENT_FAILED = SagaStep.PAYMENT_FAILED;
	private static final SagaStep NOTIFICATION_REQUEST = SagaStep.NOTIFICATION_REQUEST;
	private static final SagaStep NOTIFICATION_COMPLETE = SagaStep.NOTIFICATION_COMPLETE;
	private static final SagaStep RESERVATION_CANCEL_ROLLBACK = SagaStep.RESERVATION_CANCEL_ROLLBACK;
	private static final SagaStep RESERVATION_CANCEL_CANCELLED = SagaStep.RESERVATION_CANCEL_CANCELLED;
	private static final SagaStep COMPLETE = SagaStep.COMPLETE;

	@PostConstruct
	public void init() {
		eventBus = new EventBus();
		registerEvents();
	}

	private void registerEvents() {

	}

	@Override
	public void start(Object request) {
		ReservationCancelRequest reservationCancelRequest = (ReservationCancelRequest) request;
		saveSageState(SagaState.of(reservationCancelRequest.getId(), START));
	}

	@Override
	public void handle(Object response) {

	}

	@Override
	public void end(String id) {

	}

	private void saveSageState(SagaState sagaState) {
		sagaStateService.saveSagaState(sagaState);
	}
}
