package com.sim.notification.event;

import org.springframework.stereotype.Service;

import com.sim.notification.clients.reservation.ReservationClient;
import com.sim.notification.clients.reservation.ReservationInfo;
import com.sim.notification.email.EmailService;
import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.event.consumer.NotificationRequestEvent;
import com.sim.notification.event.factory.ReservationMessageFactory;

import lombok.RequiredArgsConstructor;

/**
 * 알림 서비스
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
	private final ReservationClient reservationClient;
	private final EmailService emailService;

	/**
	 * 예약 신청 메시지 전송
	 * @param notificationRequestEvent 예약 신청 이벤트
	 */
	@Override
	public void sendMessage(NotificationRequestEvent notificationRequestEvent) {
		ReservationInfo reservationInfo = reservationClient.getReservationInfoById(
			notificationRequestEvent.getReservationId());

		if(reservationInfo.canSendEmail()) {
			sendEmail(reservationInfo);
		}

		if(reservationInfo.canSendSms()) {
			sendSms(reservationInfo);
		}
	}

	/**
	 * 이메일 전송
	 */
	private void sendEmail(ReservationInfo reservationInfo) {
		String subject = reservationInfo.getEmail();
		ReservationCompleteMessage message = ReservationMessageFactory.createReservationCompleteMessage(
			reservationInfo);
		emailService.sendReservationCompletedEmail(subject, message);
	}

	/**
	 * sms 전송(유료라 pass)
	 */
	private void sendSms(ReservationInfo reservationInfo) {

	}
}
