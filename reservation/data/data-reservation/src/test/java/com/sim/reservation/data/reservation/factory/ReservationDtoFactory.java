package com.sim.reservation.data.reservation.factory;

import com.sim.reservation.data.reservation.dto.ReservationDto;

/**
 * ReservationDtoFactory.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.15
 */
public class ReservationDtoFactory {
	public static final String USER_ID = "test";
	public static final String NAME = "이수봉";
	public static final String PHONE_NUM = "010-5555-4444";
	public static final String EMAIL = "test@naver.com";
	public static final boolean IS_EMAIL_RECEIVE_DENIED = true;
	public static final boolean IS_SNS_RECEIVE_DENIED = true;

	public static ReservationDto createReservationDto() {
		return ReservationDto.builder()
			.userId(USER_ID)
			.name(NAME)
			.phoneNum(PHONE_NUM)
			.email(EMAIL)
			.isSnsReceiveDenied(IS_SNS_RECEIVE_DENIED)
			.isEmailReceiveDenied(IS_EMAIL_RECEIVE_DENIED)
			.build();
	}
}
