package com.sim.event.orchestration.publish;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaExternalEventPublisher implements ExternalEventPublisher{
	private final StreamBridge streamBridge;

	@Override
	public void publishPaymentRequestEvent(PaymentRequestEvent paymentRequestEvent) {
		streamBridge.send("payment.request", paymentRequestEvent);
	}

	@Override
	public void publishNotificationRequestEvent(NotificationRequestEvent notificationRequestEvent) {
		streamBridge.send("notification.request", notificationRequestEvent);
	}

	@Override
	public void publishReservationApplyRollbackEvent(ReservationApplyRollbackEvent reservationApplyRollbackEvent) {
		streamBridge.send("reservation-apply.rollback", reservationApplyRollbackEvent);
	}
}
