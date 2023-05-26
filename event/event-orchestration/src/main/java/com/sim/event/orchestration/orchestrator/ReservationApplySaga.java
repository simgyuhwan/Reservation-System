package com.sim.event.orchestration.orchestrator;

import org.springframework.stereotype.Service;

import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;
import com.sim.event.orchestration.event.ReservationCancelledEvent;
import com.sim.event.orchestration.publish.ExternalEventPublisher;
import com.sim.event.store.domain.SagaState;
import com.sim.event.store.service.SagaStateService;
import com.sim.event.store.type.SagaStep;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationApplySaga implements Saga {
	private final SagaStateService sagaStateService;
	private final ExternalEventPublisher eventPublisher;
	private EventHandler eventHandler;

	@PostConstruct
	public void init() {
		eventHandler = new EventHandler();
		eventHandler.register(ReservationApplyRequest.class, this::handleReservationApplyRequest);
		eventHandler.register(PaymentCompleteEvent.class, this::handlePaymentCompleteEvent);
		eventHandler.register(PaymentFailedEvent.class, this::handlePaymentFailedEvent);
		eventHandler.register(ReservationCancelledEvent.class, this::handleReservationCancelledEvent);
		eventHandler.register(NotificationCompleteEvent.class, this::handleNotificationCompleteEvent);
	}

	@Override
	public void startSaga(ReservationApplyRequest request) {
		sagaStateService.saveSagaState(SagaState.of(request.getId(), SagaStep.INITIATED));
		eventHandler.publish(request);
	}

	@Override
	public void response(Object response) {
		eventHandler.publish(response);
	}

	@Override
	public void complete(String id) {
		sagaStateService.saveSagaState(SagaState.of(id, SagaStep.COMPLETE));
	}

	private void handleReservationApplyRequest(ReservationApplyRequest request) {
		sagaStateService.saveSagaState(SagaState.of(request.getId(), SagaStep.PAYMENT_REQUEST));

		PaymentRequestEvent event = PaymentRequestEvent.from(request);
		eventPublisher.publishPaymentRequestEvent(event);
	}

	private void handlePaymentCompleteEvent(PaymentCompleteEvent event) {
		sagaStateService.saveSagaState(SagaState.of(event.getId(), SagaStep.PAYMENT_COMPLETE));
		sagaStateService.saveSagaState(SagaState.of(event.getId(), SagaStep.NOTIFICATION_REQUEST));

		NotificationRequestEvent notificationRequestEvent = NotificationRequestEvent.from(event);
		eventPublisher.publishNotificationRequestEvent(notificationRequestEvent);
	}

	private void handlePaymentFailedEvent(PaymentFailedEvent event) {
		sagaStateService.saveSagaState(SagaState.of(event.getId(), SagaStep.PAYMENT_FAILED));
		sagaStateService.saveSagaState(SagaState.of(event.getId(),SagaStep.RESERVATION_APPLY_ROLLBACK));

		ReservationApplyRollbackEvent applyRollbackEvent = ReservationApplyRollbackEvent.of(event);
		eventPublisher.publishReservationApplyRollbackEvent(applyRollbackEvent);
	}

	private void handleNotificationCompleteEvent(NotificationCompleteEvent event) {
		sagaStateService.saveSagaState(SagaState.of(event.getId(), SagaStep.NOTIFICATION_COMPLETE));
		complete(event.getId());
	}

	private void handleReservationCancelledEvent(ReservationCancelledEvent event) {
		sagaStateService.saveSagaState(SagaState.of(event.getId(), SagaStep.RESERVATION_APPLY_CANCELLED));
		complete(event.getId());
	}
}
