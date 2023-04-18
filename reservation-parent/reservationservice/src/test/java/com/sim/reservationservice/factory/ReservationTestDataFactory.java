package com.sim.reservationservice.factory;

import static com.sim.reservationservice.factory.ReservationTestConstants.*;

import java.util.List;

import com.sim.reservationservice.dto.request.PerformanceDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

/**
 * ReservationTestDataFactory.java
 * 예약 서비스 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.04.12
 */
public class ReservationTestDataFactory {

	public static List<PerformanceInfoDto> createPerformanceInfoList() {
		return List.of(createPerformanceInfo());
	}

	public static PerformanceInfoDto createPerformanceInfo() {
		return PerformanceInfoDto.builder()
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
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}

	public static PerformanceDto createPerformanceDto() {
		return PerformanceDto.builder()
			.performanceId(1L)
			.userId(USER_ID)
			.performanceName(NAME)
			.performanceStartDate(START_DATE_VALUE)
			.performanceEndDate(END_DATE_VALUE)
			.performanceInfo(INFO)
			.performanceType(TYPE)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.performancePlace(PLACE)
			.price(PRICE)
			.audienceCount(AUDIENCE_COUNT)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceTimes(START_TIMES_STRING)
			.build();
	}
}
