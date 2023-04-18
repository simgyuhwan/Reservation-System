package com.reservation.performanceservice.factory;

import static com.reservation.performanceservice.factory.PerformanceTestConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.common.type.PerformanceType;
import com.reservation.performanceservice.dto.request.PerformanceDto;

public class PerformanceTestDataFactory {

	public static PerformanceDto createPerformanceDto() {
		return PerformanceDto.builder()
			.userId(USER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(PERFORMANCE_START_DATE)
			.performanceEndDate(PERFORMANCE_END_DATE)
			.performanceTimes(PERFORMANCE_TIMES)
			.performanceType(PERFORMANCE_TYPE)
			.audienceCount(AUDIENCE_COUNT)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUMBER)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceInfo(PERFORMANCE_INFO)
			.performancePlace(PERFORMANCE_PLACE)
			.build();
	}

	public static PerformanceDto createPerformanceDto(String userId, String performanceName,String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceInfo, String performancePlace) {
		return PerformanceDto.builder()
			.userId(userId)
			.performanceName(performanceName)
			.performanceStartDate(performanceStartDt)
			.performanceEndDate(performanceEndDt)
			.performanceTimes(performanceTimes)
			.performanceType(performanceType)
			.audienceCount(audienceCount)
			.price(price)
			.contactPhoneNum(contactPhoneNum)
			.contactPersonName(contactPersonName)
			.performanceInfo(performanceInfo)
			.performancePlace(performancePlace)
			.build();
	}

	public static PerformanceDto createPerformanceDto(String startDt, String endDt) {
		return PerformanceDto.builder()
			.userId(USER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(startDt)
			.performanceEndDate(endDt)
			.performanceTimes(PERFORMANCE_TIMES)
			.performanceType(PERFORMANCE_TYPE)
			.audienceCount(AUDIENCE_COUNT)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUMBER)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceInfo(PERFORMANCE_INFO)
			.performancePlace(PERFORMANCE_PLACE)
			.build();
	}

	public static List<PerformanceDto> createPerformanceDtoList() {
		return List.of(createPerformanceDto("2100-01-01", "2200-01-01"),
			createPerformanceDto("2200-01-01", "2300-01-01"));
	}

	public static Performance createPerformance() {
		return Performance.of(USER_ID, PERFORMANCE_NAME, PerformanceType.CONCERT, AUDIENCE_COUNT, PRICE, CONTACT_PHONE_NUMBER,
			CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE, createDefaultPerformanceDays());
	}

	public static List<Performance> createPerformanceList() {
		List<Performance> performances = new ArrayList<>();
		performances.add(createPerformance());
		return performances;
	}

	public static List<PerformanceDay> createDefaultPerformanceDays() {
		List<PerformanceDay> performanceDays = new ArrayList<>();
		performanceDays.add(createPerformanceDay());
		return performanceDays;
	}

	public static PerformanceDay createPerformanceDay() {
		return PerformanceDay.builder()
			.performance(null)
			.start(LocalDate.now())
			.end(LocalDate.now().plusYears(1))
			.time(LocalTime.now())
			.build();
	}

	public static List<PerformanceDay> createPerformanceDays() {
		PerformanceDto dto = createPerformanceDto();
		return dto.toPerformanceDays(createPerformance());
	}
}
