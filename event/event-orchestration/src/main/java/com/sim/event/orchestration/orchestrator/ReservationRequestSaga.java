package com.sim.event.orchestration.orchestrator;

import org.springframework.stereotype.Service;

import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.ReservationApplyCompleteEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;
import com.sim.event.orchestration.event.ReservationCancelEvent;
import com.sim.event.orchestration.publish.ExternalEventPublisher;
import com.sim.event.store.domain.SagaState;
import com.sim.event.store.service.SagaStateService;
import com.sim.event.store.type.SagaStep;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationRequestSaga implements Saga {
	private final SagaStateService sagaStateService;
	private final ExternalEventPublisher eventPublisher;
	private EventBus eventBus;

	private static final SagaStep START = SagaStep.START;
	private static final SagaStep PAYMENT_REQUEST = SagaStep.PAYMENT_REQUEST;
	private static final SagaStep PAYMENT_COMPLETE = SagaStep.PAYMENT_COMPLETE;
	private static final SagaStep PAYMENT_FAILED = SagaStep.PAYMENT_FAILED;
	private static final SagaStep NOTIFICATION_REQUEST = SagaStep.NOTIFICATION_REQUEST;
	private static final SagaStep NOTIFICATION_COMPLETE = SagaStep.NOTIFICATION_COMPLETE;
	private static final SagaStep RESERVATION_APPLY_ROLLBACK = SagaStep.RESERVATION_APPLY_ROLLBACK;
	private static final SagaStep RESERVATION_APPLY_CANCELLED = SagaStep.RESERVATION_APPLY_CANCELLED;
	private static final SagaStep COMPLETE = SagaStep.COMPLETE;

	@PostConstruct
	public void init() {
		eventBus = new EventBus();
		registerEvents();
	}

	private void registerEvents() {
		eventBus.register(ReservationApplyRequest.class, this::handleReservationApplyRequest);
		eventBus.register(PaymentCompleteEvent.class, this::handlePaymentCompleteEvent);
		eventBus.register(PaymentFailedEvent.class, this::handlePaymentFailedEvent);
		eventBus.register(ReservationCancelEvent.class, this::handleReservationCancelEvent);
		eventBus.register(NotificationCompleteEvent.class, this::handleNotificationCompleteEvent);
	}

	@Override
	public void start(ReservationApplyRequest request) {
		saveSageState(SagaState.of(request.getId(), START));
		eventBus.publish(request);
	}

	@Override
	public void handle(Object response) {
		eventBus.publish(response);
	}

	@Override
	public void end(String id) {
		sagaStateService.saveSagaState(SagaState.of(id, COMPLETE));
	}

	private void handleReservationApplyRequest(ReservationApplyRequest request) {
		saveSageState(SagaState.of(request.getId(), PAYMENT_REQUEST));

		PaymentRequestEvent event = PaymentRequestEvent.from(request);
		eventPublisher.publish(event);
	}

	private void handlePaymentCompleteEvent(PaymentCompleteEvent event) {
		saveSageState(SagaState.of(event.getId(), PAYMENT_COMPLETE));
		saveSageState(SagaState.of(event.getId(), NOTIFICATION_REQUEST));

		publishNotificationRequestEvent(event);
	}

	private void handlePaymentFailedEvent(PaymentFailedEvent event) {
		saveSageState(SagaState.of(event.getId(), PAYMENT_FAILED));
		saveSageState(SagaState.of(event.getId(), RESERVATION_APPLY_ROLLBACK));

		publishReservationApplyRollbackEvent(event);
	}

	private void handleNotificationCompleteEvent(NotificationCompleteEvent event) {
		saveSageState(SagaState.of(event.getId(), NOTIFICATION_COMPLETE));
		end(event.getId());

		publishReservationApplyCompleteEvent(event);
	}

	private void handleReservationCancelEvent(ReservationCancelEvent event) {
		saveSageState(SagaState.of(event.getId(), RESERVATION_APPLY_CANCELLED));
		end(event.getId());
	}

	private void saveSageState(SagaState sagaState) {
		sagaStateService.saveSagaState(sagaState);
	}

	private void publishReservationApplyRollbackEvent(PaymentFailedEvent event) {
		ReservationApplyRollbackEvent applyRollbackEvent = ReservationApplyRollbackEvent.of(event);
		eventPublisher.publish(applyRollbackEvent);
	}

	private void publishNotificationRequestEvent(PaymentCompleteEvent event) {
		NotificationRequestEvent notificationRequestEvent = NotificationRequestEvent.from(event);
		eventPublisher.publish(notificationRequestEvent);
	}

	private void publishReservationApplyCompleteEvent(NotificationCompleteEvent event) {
		ReservationApplyCompleteEvent reservationApplyCompleteEvent = ReservationApplyCompleteEvent.from(event);
		eventPublisher.publish(reservationApplyCompleteEvent);
	}

}
