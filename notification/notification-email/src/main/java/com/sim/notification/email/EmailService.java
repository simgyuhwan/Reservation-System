package com.sim.notification.email;

import com.sim.notification.common.ReservationCompleteMessage;

public interface EmailService {
	void sendReservationCompletedEmail(String to, ReservationCompleteMessage reservationCompleteMessage);
}
