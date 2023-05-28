package com.sim.notification.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.email.message.ReservationTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender mailSender;

	@Async
	@Override
	public void sendReservationCompletedEmail(String to, ReservationCompleteMessage reservationCompleteMessage) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setSubject(ReservationTemplate.generateSubject(reservationCompleteMessage));
			helper.setText(ReservationTemplate.generateBody(reservationCompleteMessage) ,true);
			helper.setTo(to);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
