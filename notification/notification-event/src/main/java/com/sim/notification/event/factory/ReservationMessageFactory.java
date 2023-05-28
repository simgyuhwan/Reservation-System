package com.sim.notification.event.factory;

import com.sim.notification.clients.reservation.ReservationInfo;
import com.sim.notification.email.message.ReservationCompleteMessage;

public class ReservationMessageFactory {
	public static ReservationCompleteMessage createReservationCompleteMessage(ReservationInfo reservationInfo) {
		return ReservationCompleteMessage.builder()
			.username(reservationInfo.getUsername())
			.performanceName(reservationInfo.getPerformanceName())
			.place(reservationInfo.getPlace())
			.startDate(reservationInfo.getStartDate())
			.startTime(reservationInfo.getStartTime())
			.build();
	}
}
