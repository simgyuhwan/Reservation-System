package com.sim.member.api.factory;

import java.util.List;
import java.util.Set;

import com.sim.member.memberdomain.dto.PerformanceDto;

public class PerformanceDtoFactory {
	public static final Long PERFORMANCE_ID = 1L;
	public static final Long MEMBER_ID = 1L;
	public static final String PERFORMANCE_NAME = "가디언즈 오브 갤럭시3";
	public static final String PERFORMANCE_START_DATE = "2025-05-04";
	public static final String PERFORMANCE_END_DATE = "2029-05-04";
	public static final String PERFORMANCE_TYPE = "THEATER";
	public static final int AUDIENCE_COUNT = 150;
	public static final int PRICE = 15000;
	public static final String CONTACT_PHONE_NUM = "010-5555-4444";
	public static final String CONTACT_PERSON_NAME = "김혜수";
	public static final String PERFORMANCE_INFO = "가족같은 영화!";
	public static final String PERFORMANCE_PLACE = "홍대 5극장";
	public static final Set<String> PERFORMANCE_TIMES = Set.of("11:00", "13:00") ;
	
	public static List<PerformanceDto> createPerformanceDtoList() {
		PerformanceDto performanceDto = PerformanceDto.builder()
			.performanceId(PERFORMANCE_ID)
			.memberId(MEMBER_ID)
			.performanceName(PERFORMANCE_NAME)
			.performanceStartDate(PERFORMANCE_START_DATE)
			.performanceEndDate(PERFORMANCE_END_DATE)
			.audienceCount(AUDIENCE_COUNT)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceInfo(PERFORMANCE_INFO)
			.performancePlace(PERFORMANCE_PLACE)
			.performanceTimes(PERFORMANCE_TIMES)
			.build();

		return List.of(performanceDto);
	}
}
