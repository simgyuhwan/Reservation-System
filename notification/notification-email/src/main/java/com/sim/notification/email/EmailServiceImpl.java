package com.sim.notification.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sim.notification.common.ReservationCompleteMessage;
import com.sim.notification.common.ReservationTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender mailSender;

	@Override
	public void sendReservationCompletedEmail(String to, ReservationCompleteMessage reservationCompleteMessage) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setSubject(ReservationTemplate.generateBody(reservationCompleteMessage));
			helper.setText(ReservationTemplate.generateBody(reservationCompleteMessage) ,true);
			helper.setTo(to);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
