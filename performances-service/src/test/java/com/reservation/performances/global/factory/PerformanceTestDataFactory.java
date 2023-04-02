package com.reservation.performances.global.factory;

import java.util.Set;

import com.reservation.performances.dto.request.PerformanceRegisterDto;

public class PerformanceTestDataFactory {

	public final static String REGISTER = "홍길동";
	public final static String PERFORMANCE_START_DATE = "2023-06-01";
	public final static String PERFORMANCE_END_DATE = "2023-10-01";
	public final static Set<String> PERFORMANCE_TIMES = Set.of("12:00", "14:00", "15:00");
	public final static String PERFORMANCE_TYPE = "Action";
	public final static Integer AUDIENCE_COUNT = 140;
	public final static Integer PRICE = 14000;
	public final static String CONTACT_PHONE_NUMBER = "010-1234-2345";
	public final static String CONTACT_PERSON_NAME = "홍길동";
	public final static String PERFORMANCE_INFO = "공연 소개";
	public final static String PERFORMANCE_PLACE = "홍대 제 1극장";

	public static PerformanceRegisterDto createPerformanceRegisterDto() {
		return PerformanceRegisterDto.builder()
			.register(REGISTER)
			.performanceStartDt(PERFORMANCE_START_DATE)
			.performanceEndDt(PERFORMANCE_END_DATE)
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

	public static PerformanceRegisterDto createPerformanceRegisterDto(String register, String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceInfo, String performancePlace) {
		return PerformanceRegisterDto.builder()
			.register(register)
			.performanceStartDt(performanceStartDt)
			.performanceEndDt(performanceEndDt)
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

	public static PerformanceRegisterDto createPerformanceRegisterDto(String startDt, String endDt) {
		return PerformanceRegisterDto.builder()
			.register(REGISTER)
			.performanceStartDt(startDt)
			.performanceEndDt(endDt)
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
}
