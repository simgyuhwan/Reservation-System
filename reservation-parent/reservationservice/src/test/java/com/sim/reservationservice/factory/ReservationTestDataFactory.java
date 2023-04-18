package com.sim.reservationservice.factory;

import static com.sim.reservationservice.factory.ReservationTestConstants.*;

import java.util.List;

import com.sim.reservationservice.dto.response.PerformanceInfo;

/**
 * ReservationTestDataFactory.java
 * 예약 서비스 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.04.12
 */
public class ReservationTestDataFactory {

	public static List<PerformanceInfo> createPerformanceInfoList() {
		return List.of(createPerformanceInfo());
	}

	public static PerformanceInfo createPerformanceInfo() {
		return PerformanceInfo.builder()
			.name(NAME)
			.info(INFO)
			.type(TYPE)
			.startDate(START_DATE)
			.endDate(END_DATE)
			.startTimes(START_TIMES)
			.place(PLACE)
			.isAvailable(IS_AVAILABLE)
			.audienceCount(AUDIENCE_COUNT)
			.availableSeats(AVAILABLE_SEATS)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.build();
	}
}
