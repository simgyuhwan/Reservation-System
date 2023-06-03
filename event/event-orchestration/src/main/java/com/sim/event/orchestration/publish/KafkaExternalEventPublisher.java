package com.sim.event.orchestration.publish;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentRefundEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.ReservationApplyCompleteEvent;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaExternalEventPublisher implements ExternalEventPublisher{
	private final StreamBridge streamBridge;

	@Override
	public void publish(PaymentRequestEvent paymentRequestEvent) {
		streamBridge.send("payment.request", paymentRequestEvent);
	}

	@Override
	public void publish(NotificationRequestEvent notificationRequestEvent) {
		streamBridge.send("notification.request", notificationRequestEvent);
	}

	@Override
	public void publish(ReservationApplyRollbackEvent reservationApplyRollbackEvent) {
		streamBridge.send("reservation-apply.rollback", reservationApplyRollbackEvent);
	}

	@Override
	public void publish(ReservationApplyCompleteEvent reservationApplyCompleteEvent) {
		streamBridge.send("reservation-apply.complete", reservationApplyCompleteEvent);
	}

	@Override
	public void publish(PaymentRefundEvent paymentRefundEvent) {
		streamBridge.send("payment-refund-request", paymentRefundEvent);
	}
}
