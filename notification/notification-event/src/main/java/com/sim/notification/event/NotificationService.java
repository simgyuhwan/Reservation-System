package com.sim.notification.event;

import com.sim.notification.event.consumer.ReservationApplyEvent;
import com.sim.notification.event.consumer.ReservationCancelEvent;

/**
 * 알림 서비스
 */
public interface NotificationService {
	/**
	 * 예약 신청 메시지 전송
	 *
	 * @param reservationApplyEvent 예약 신청 이벤트
	 */
	void sendMessage(ReservationApplyEvent reservationApplyEvent);

	/**
	 * 예약 신청 취소 메시지 전송
	 *
	 * @param reservationCancelEvent 예약 신청 취소 이넵트
	 */
	void sendMessage(ReservationCancelEvent reservationCancelEvent);
}
