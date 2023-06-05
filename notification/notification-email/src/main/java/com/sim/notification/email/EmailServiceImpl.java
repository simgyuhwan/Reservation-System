package com.sim.notification.email;

import java.util.function.Function;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sim.notification.email.message.ReservationCancelMessage;
import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.email.message.ReservationTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * 이메일 서비스
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender mailSender;

	/**
	 * 예약 완료 이메일 전송
	 *
	 * @param to 받는 이메일
	 * @param reservationCompleteMessage 예약 완료 메시지
	 */
	@Async
	@Override
	public void sendEmail(String to, ReservationCompleteMessage reservationCompleteMessage) {
		sendEmail(to, reservationCompleteMessage, ReservationTemplate::generateSubject, ReservationTemplate::generateBody);
	}

	/**
	 * 예약 취소 이메일 전송
	 *
	 * @param to 받는 이메일
	 * @param reservationCancelMessage 예약 취소 이메일
	 */
	@Async
	@Override
	public void sendEmail(String to, ReservationCancelMessage reservationCancelMessage) {
		sendEmail(to, reservationCancelMessage, ReservationTemplate::generateSubject, ReservationTemplate::generateBody);
	}

	private <T> void sendEmail(String to, T message, Function<T, String> subjectGenerator, Function<T, String> bodyGenerator) {
		try{
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject(subjectGenerator.apply(message));
			helper.setText(bodyGenerator.apply(message) ,true);
			helper.setTo(to);

			mailSender.send(mimeMessage);
		}catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
