package com.sim.performance.factory;

import java.util.Set;

import com.sim.performance.dto.request.PerformanceUpdateRequest;

public class PerformanceUpdateRequestFactory {
	public static final Long MEMBER_ID = 1L;
	public static final String PERFORMANCE_NAME = "오페라의 유령";
	public static final String PERFORMANCE_START_DATE = "2023-06-01";
	public static final String PERFORMANCE_END_DATE = "2023-10-01";
	public static final Set<String> PERFORMANCE_TIMES = Set.of("12:00", "14:00", "15:00");
	public static final String PERFORMANCE_TYPE = "CONCERT";
	public static final Integer AUDIENCE_COUNT = 140;
	public static final Integer PRICE = 14000;
	public static final String CONTACT_PHONE_NUMBER = "010-1234-2345";
	public static final String CONTACT_PERSON_NAME = "홍길동";
	public static final String PERFORMANCE_INFO = "공연 소개";
	public static final String PERFORMANCE_PLACE = "홍대 제 1극장";

	public static PerformanceUpdateRequest createPerformanceUpdateRequest() {
		return PerformanceUpdateRequest.builder()
			.memberId(MEMBER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(PERFORMANCE_START_DATE)
			.performanceEndDate(PERFORMANCE_END_DATE)
			.performanceTimes(PERFORMANCE_TIMES)
			.performanceType(PERFORMANCE_TYPE)
			.audienceCount(AUDIENCE_COUNT)
			.price(PRICE)
			.contactPersonName(CONTACT_PERSON_NAME)
			.contactPhoneNum(CONTACT_PHONE_NUMBER)
			.performanceInfo(PERFORMANCE_INFO)
			.performancePlace(PERFORMANCE_PLACE)
			.build();
	}
}
