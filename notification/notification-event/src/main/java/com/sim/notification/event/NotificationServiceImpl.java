package com.sim.notification.event;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.sim.notification.clients.reservation.ReservationClient;
import com.sim.notification.clients.reservation.ReservationInfo;
import com.sim.notification.email.EmailService;
import com.sim.notification.email.message.ReservationCancelMessage;
import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.event.consumer.ReservationApplyEvent;
import com.sim.notification.event.consumer.ReservationCancelEvent;
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
	 *
	 * @param reservationApplyEvent 예약 신청 이벤트
	 */
	@Override
	public void sendMessage(ReservationApplyEvent reservationApplyEvent) {
		sendMessage(reservationApplyEvent.getReservationId(), this::sendReservationCompleteEmail, this::sendReservationApplySms);
	}

	/**
	 * 예약 신청 취소 이벤트
	 *
	 * @param reservationCancelEvent 예약 신청 취소 이넵트
	 */
	@Override
	public void sendMessage(ReservationCancelEvent reservationCancelEvent) {
		sendMessage(reservationCancelEvent.getReservationId(), this::sendReservationCancelEmail, this::sendReservationCancelSms);
	}

	/**
	 * 예약 완료 이메일 전송
	 */
	private void sendReservationCompleteEmail(ReservationInfo reservationInfo) {
		String subject = reservationInfo.getEmail();
		ReservationCompleteMessage message = ReservationMessageFactory.createReservationCompleteMessage(
			reservationInfo);

		emailService.sendEmail(subject, message);
	}

	/**
	 * 예약 완료 sms 전송(유료라 pass)
	 */
	private void sendReservationApplySms(ReservationInfo reservationInfo) {
		String subject = reservationInfo.getEmail();
		ReservationCancelMessage message = ReservationMessageFactory.createReservationCancelMessage(
			reservationInfo);

		emailService.sendEmail(subject, message);
	}

	/**
	 * 예약 취소 이메일 전송
	 * 
	 * @param reservationInfo 예약 정보
	 */
	private void sendReservationCancelEmail(ReservationInfo reservationInfo) {
		String subject = reservationInfo.getEmail();
		ReservationCancelMessage reservationCancelMessage = ReservationMessageFactory.createReservationCancelMessage(
			reservationInfo);

		emailService.sendEmail(subject, reservationCancelMessage);
	}

	/**
	 * 예약 취소 SMS 전송
	 * 
	 * @param reservationInfo 예약 정보
	 */
	private void sendReservationCancelSms(ReservationInfo reservationInfo) {

	}

	/**
	 * 메세지 전송
	 * 
	 * @param reservationId 예약 ID
	 * @param emailSender email 전송 함수
	 * @param snsSender sns 전송 함수
	 */
	private void sendMessage(Long reservationId, Consumer<ReservationInfo> emailSender, Consumer<ReservationInfo> smsSender) {
		ReservationInfo reservationInfo = reservationClient.getReservationInfoById(
			reservationId);

		if(reservationInfo.canSendEmail()) {
			emailSender.accept(reservationInfo);
		}

		if(reservationInfo.canSendSms()) {
			smsSender.accept(reservationInfo);
		}
	}

}
