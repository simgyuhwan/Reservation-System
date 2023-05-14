package com.sim.performance.factory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.type.PerformanceType;

/**
 * PerformanceFactory.java
 * Performance 생성 Factory 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
public class PerformanceFactory {
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

	public static Performance createPerformance() {
		return Performance.of(MEMBER_ID, PERFORMANCE_NAME, PerformanceType.CONCERT, AUDIENCE_COUNT, PRICE, CONTACT_PHONE_NUMBER,
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

}
