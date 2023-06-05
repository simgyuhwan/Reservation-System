package com.sim.notification.event.factory;

import com.sim.notification.clients.reservation.ReservationInfo;
import com.sim.notification.email.message.ReservationCancelMessage;
import com.sim.notification.email.message.ReservationCompleteMessage;
import com.sim.notification.event.consumer.ReservationCancelEvent;

/**
 * 예약 메시지 Factory 클래스
 */
public class ReservationMessageFactory {

	/**
	 * 예약 완료 메시지 생성
	 *
	 * @param reservationInfo 예약 정보
	 * @return 예약 완료 메시지
	 */
	public static ReservationCompleteMessage createReservationCompleteMessage(ReservationInfo reservationInfo) {
		return ReservationCompleteMessage.builder()
			.username(reservationInfo.getUsername())
			.performanceName(reservationInfo.getPerformanceName())
			.place(reservationInfo.getPlace())
			.startDate(reservationInfo.getStartDate())
			.startTime(reservationInfo.getStartTime())
			.build();
	}

	public static ReservationCancelMessage createReservationCancelMessage(ReservationInfo reservationInfo) {
		return ReservationCancelMessage.builder()
			.username(reservationInfo.getUsername())
			.performanceName(reservationInfo.getPerformanceName())
			.place(reservationInfo.getPlace())
			.startDate(reservationInfo.getStartDate())
			.startTime(reservationInfo.getStartTime())
			.build();
	}
}
