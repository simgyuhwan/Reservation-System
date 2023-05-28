package com.sim.notification.email;

import com.sim.notification.email.message.ReservationCompleteMessage;

public interface EmailService {
	void sendReservationCompletedEmail(String to, ReservationCompleteMessage reservationCompleteMessage);
}
