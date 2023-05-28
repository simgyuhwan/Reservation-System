package com.sim.notification.event;

import org.springframework.stereotype.Service;

import com.sim.notification.clients.reservation.ReservationClient;
import com.sim.notification.clients.reservation.ReservationInfo;
import com.sim.notification.email.EmailService;
import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.event.consumer.NotificationRequestEvent;
import com.sim.notification.event.factory.ReservationMessageFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
	private final ReservationClient reservationClient;
	private final EmailService emailService;

	@Override
	public void sendMessage(NotificationRequestEvent notificationRequestEvent) {
		ReservationInfo reservationInfo = reservationClient.getReservationInfoById(
			notificationRequestEvent.getReservationId());

		if(reservationInfo.canSendEmail()) {
			sendEmail(reservationInfo);
		}

		if(reservationInfo.canSendSns()) {
			sendSns(reservationInfo);
		}
	}

	private void sendEmail(ReservationInfo reservationInfo) {
		String subject = reservationInfo.getEmail();
		ReservationCompleteMessage message = ReservationMessageFactory.createReservationCompleteMessage(
			reservationInfo);
		emailService.sendReservationCompletedEmail(subject, message);
	}

	private void sendSns(ReservationInfo reservationInfo) {

	}
}
