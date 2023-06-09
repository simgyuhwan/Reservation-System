package com.sim.event.orchestration.publish;

import com.sim.event.orchestration.event.NotificationRequestEvent;
import com.sim.event.orchestration.event.PaymentRefundEvent;
import com.sim.event.orchestration.event.PaymentRequestEvent;
import com.sim.event.orchestration.event.RefundNotificationEvent;
import com.sim.event.orchestration.event.ReservationApplyCompleteEvent;
import com.sim.event.orchestration.event.ReservationApplyRollbackEvent;
import com.sim.event.orchestration.event.ReservationCancelCompleteEvent;

/**
 * 외부 이벤트 발행 인터페이스
 */
public interface ExternalEventPublisher {

	/**
	 * 예약 요청 이벤트 발행
	 */
	void publish(PaymentRequestEvent paymentRequestEvent);

	/**
	 * 알림 요청 이벤트 발행
	 */
	void publish(NotificationRequestEvent notificationRequestEvent);

	/**
	 * 예약 신청 롤백 이벤트 발행
	 */
	void publish(ReservationApplyRollbackEvent reservationApplyRollbackEvent);

	/**
	 * 예약 신청 완료 이벤트 발행
	 */
	void publish(ReservationApplyCompleteEvent reservationApplyCompleteEvent);

	/**
	 * 결제 취소 요청
	 */
	void publish(PaymentRefundEvent paymentRefundEvent);

	/**
	 * 결제 취소 알림 이벤트 발행
	 */
	void publish(RefundNotificationEvent refundNotificationEvent);

	/**
	 * 결제 취소 완료 이벤트 발행
	 */
	void publish(ReservationCancelCompleteEvent reservationCancelCompleteEvent);
}
