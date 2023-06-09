package com.sim.event.store.type;

public enum SagaStep {
	START,
	PAYMENT_REQUEST,
	PAYMENT_COMPLETE,
	PAYMENT_REFUND_REQUEST,
	PAYMENT_REFUND_COMPLETE,
	PAYMENT_REFUND_FAILED,
	PAYMENT_FAILED,
	PAYMENT_ROLLBACK,
	NOTIFICATION_REQUEST,
	NOTIFICATION_COMPLETE,
	RESERVATION_APPLY_ROLLBACK,
	RESERVATION_APPLY_COMPLETE,
	RESERVATION_APPLY_CANCELLED,
	RESERVATION_CANCEL_ROLLBACK,
	RESERVATION_CANCEL_COMPLETE,
	RESERVATION_CANCEL_CANCELLED,
	COMPLETE
}
