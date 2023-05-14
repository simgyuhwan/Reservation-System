package com.reservation.factory;

import java.util.List;
import java.util.Set;

import com.reservation.common.dto.PerformanceDto;

/**
 * PerformanceDtoFactory.java
 * PerformanceDto Factory 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
public class PerformanceDtoFactory {
	public final static String USER_ID = "test";
	public final static String PERFORMANCE_NAME = "오페라의 유령";
	public final static String PERFORMANCE_START_DATE = "2023-06-01";
	public final static String PERFORMANCE_END_DATE = "2023-10-01";
	public final static Set<String> PERFORMANCE_TIMES = Set.of("12:00", "14:00", "15:00");
	public final static String PERFORMANCE_TYPE = "CONCERT";
	public final static Integer AUDIENCE_COUNT = 140;
	public final static Integer PRICE = 14000;
	public final static String CONTACT_PHONE_NUMBER = "010-1234-2345";
	public final static String CONTACT_PERSON_NAME = "홍길동";
	public final static String PERFORMANCE_INFO = "공연 소개";
	public final static String PERFORMANCE_PLACE = "홍대 제 1극장";
	public final static Long MEMBER_ID = 1L;

	public static PerformanceDto createPerformanceDto() {
		return PerformanceDto.builder()
			.memberId(MEMBER_ID)
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

	public static  List<PerformanceDto> createPerformanceDtoList() {
		return List.of(createPerformanceDto());
	}
}
