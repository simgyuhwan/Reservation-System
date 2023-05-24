package com.sim.event.orchestration.orchestrator;

import com.sim.event.orchestration.event.NotificationCompleteEvent;
import com.sim.event.orchestration.event.PaymentCompleteEvent;
import com.sim.event.orchestration.event.PaymentFailedEvent;
import com.sim.event.orchestration.event.ReservationApplyRequest;
import com.sim.event.orchestration.event.ReservationCancelledEvent;

/**
 * 	INITIATED,
 * 	PAYMENT_REQUEST,
 * 	PAYMENT_RESPONSE,
 * 	PAYMENT_ROLLBACK,
 * 	NOTIFICATION_REQUEST,
 * 	NOTIFICATION_RESPONSE,
 * 	RESERVATION_APPLY_ROLLBACK,
 * 	RESERVATION_APPLY_COMPLETE
 */
public interface ReservationApplySaga {
	void startSaga(ReservationApplyRequest request);

	void response(Object response);

	void handleReservationApplyRequest(ReservationApplyRequest event);

	void handlePaymentCompleteEvent(PaymentCompleteEvent event);

	void handlePaymentFailedEvent(PaymentFailedEvent event);

	void handleNotificationCompleteEvent(NotificationCompleteEvent event);

	void handleReservationCancelledEvent(ReservationCancelledEvent event);

	void complete(String id);
}
