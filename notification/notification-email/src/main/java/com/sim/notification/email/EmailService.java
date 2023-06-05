package com.sim.notification.email;

import com.sim.notification.email.message.ReservationCancelMessage;
import com.sim.notification.email.message.ReservationCompleteMessage;

/**
 * 이메일 서비스
 */
public interface EmailService {
	/**
	 * 예약 완료 이메일 전송
	 *
	 * @param to 받는 이메일
	 * @param reservationCompleteMessage 예약 완료 메시지
	 */
	void sendEmail(String to, ReservationCompleteMessage reservationCompleteMessage);

	/**
	 * 예약 취소 이메일 전송
	 *
	 * @param to 받는 이메일
	 * @param reservationCancelMessage 예약 취소 이메일
	 */
	void sendEmail(String to, ReservationCancelMessage reservationCancelMessage);
}
