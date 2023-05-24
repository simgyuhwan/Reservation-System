package com.sim.event.orchestration.publish;

import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;

public interface ExternalEventPublisher {

	void publishPaymentRequestEvent(PaymentRequestEvent paymentRequestEvent);

	void publishNotificationRequestEvent(NotificationRequestEvent notificationRequestEvent);

	void publishReservationApplyRollbackEvent(ReservationApplyRollbackEvent reservationApplyRollbackEvent);
}
