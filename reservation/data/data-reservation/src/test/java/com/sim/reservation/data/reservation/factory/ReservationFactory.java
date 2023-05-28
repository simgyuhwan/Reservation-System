package com.sim.reservation.data.reservation.factory;

import com.sim.reservation.data.reservation.domain.Reservation;

/**
 * ReservationFactory.java
 *
 * @author sgh
 * @since 2023.05.15
 */
public class ReservationFactory {
	public static final String USER_ID = "test";
	public static final String NAME = "이수봉";
	public static final String PHONE_NUM = "010-5555-4444";
	public static final String EMAIL = "test@naver.com";
	public static final boolean IS_EMAIL_RECEIVE_DENIED = true;
	public static final boolean IS_SMS_RECEIVE_DENIED = true;

	public static Reservation createReservation() {
		return Reservation.builder()
			.userId(USER_ID)
			.name(NAME)
			.phoneNum(PHONE_NUM)
			.email(EMAIL)
			.isEmailReceiveDenied(IS_EMAIL_RECEIVE_DENIED)
			.isSmsReceiveDenied(IS_SMS_RECEIVE_DENIED)
			.build();
	}

	public static Reservation createReservationWithId(Long id) {
		return Reservation.builder()
			.id(id)
			.userId(USER_ID)
			.name(NAME)
			.phoneNum(PHONE_NUM)
			.email(EMAIL)
			.isEmailReceiveDenied(IS_EMAIL_RECEIVE_DENIED)
			.isSmsReceiveDenied(IS_SMS_RECEIVE_DENIED)
			.build();
	}
}
