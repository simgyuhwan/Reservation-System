package com.sim.performance.domain.factory;

import java.util.ArrayList;
import java.util.Set;

import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.type.PerformanceType;

public class PerformanceUpdateDtoFactory {
	public final static Long MEMBER_ID = 1L;
	public final static String PERFORMANCE_NAME = "오페라의 유령";
	public final static String PERFORMANCE_START_DATE = "2023-06-01";
	public final static String PERFORMANCE_END_DATE = "2023-10-01";
	public final static Set<String> PERFORMANCE_TIMES = Set.of("12:00", "14:00", "15:00");
	public final static PerformanceType PERFORMANCE_TYPE = PerformanceType.CONCERT;
	public final static Integer AUDIENCE_COUNT = 140;
	public final static Integer PRICE = 14000;
	public final static String CONTACT_PHONE_NUMBER = "010-1234-2345";
	public final static String CONTACT_PERSON_NAME = "홍길동";
	public final static String PERFORMANCE_INFO = "공연 소개";
	public final static String PERFORMANCE_PLACE = "홍대 제 1극장";

	public static PerformanceUpdateDto createPerformanceUpdateDto() {
		return PerformanceUpdateDto.builder()
			.memberId(MEMBER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(PERFORMANCE_START_DATE)
			.performanceEndDate(PERFORMANCE_END_DATE)
			.performanceDays(new ArrayList<>())
			.performanceType(PERFORMANCE_TYPE)
			.audienceCount(AUDIENCE_COUNT)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUMBER)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceInfo(PERFORMANCE_INFO)
			.performancePlace(PERFORMANCE_PLACE)
			.build();
	}

	public static PerformanceUpdateDto createPerformanceUpdateDto(String startDate, String endDate) {
		return PerformanceUpdateDto.builder()
			.memberId(MEMBER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(startDate)
			.performanceEndDate(endDate)
			.performanceDays(new ArrayList<>())
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
