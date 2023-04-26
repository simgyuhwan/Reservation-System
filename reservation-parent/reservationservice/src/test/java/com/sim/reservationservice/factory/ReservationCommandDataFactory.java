package com.sim.reservationservice.factory;

import static com.sim.reservationservice.factory.ReservationCommandConstants.*;

import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;

/**
 * ReservationCommandDataFactory.java
 * 예약 서비스 command 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
public class ReservationCommandDataFactory {

	public static ReservationDto createReservationDto() {
		return ReservationDto.builder()
			.userId(USER_ID)
			.isEmailReceiveDenied(IS_EMAIL_RECEIVE_DENIED)
			.isSnsReceiveDenied(IS_SNS_RECEIVE_DENIED)
			.name(NAME)
			.phoneNum(PHONE_NUM)
			.email(EMAIL)
			.build();
	}

	public static ReservationDto createReservationDto(String userId, String name, String phoneNum, String email) {
		return ReservationDto.builder()
			.userId(userId)
			.isEmailReceiveDenied(IS_EMAIL_RECEIVE_DENIED)
			.isSnsReceiveDenied(IS_SNS_RECEIVE_DENIED)
			.name(name)
			.phoneNum(phoneNum)
			.email(email)
			.build();
	}

	public static ReservationInfoDto createReservationInfoDto() {
		return ReservationInfoDto.builder()
			.id(1L)
			.name(NAME)
			.phoneNum(PHONE_NUM)
			.performanceName(PERFORMANCE_NAME)
			.date(PERFORMANCE_DATE)
			.time(PERFORMANCE_TIME)
			.build();
	}
}
