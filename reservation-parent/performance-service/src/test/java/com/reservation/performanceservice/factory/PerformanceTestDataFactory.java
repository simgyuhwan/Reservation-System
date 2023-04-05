package com.reservation.performanceservice.factory;

import java.util.List;
import java.util.Set;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.domain.PerformanceType;
import com.reservation.performanceservice.dto.request.PerformanceDto;

public class PerformanceTestDataFactory {
	public final static String USER_ID = "test1";
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

	public static PerformanceDto createPerformanceRegisterDto() {
		return PerformanceDto.builder()
			.userId(USER_ID)
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

	public static PerformanceDto createPerformanceRegisterDto(String userId, String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceInfo, String performancePlace) {
		return PerformanceDto.builder()
			.userId(userId)
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

	public static PerformanceDto createPerformanceRegisterDto(String startDt, String endDt) {
		return PerformanceDto.builder()
			.userId(USER_ID)
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

	public static Performance createPerformance() {
		return Performance.of(USER_ID, PerformanceType.CONCERT, AUDIENCE_COUNT, PRICE, CONTACT_PHONE_NUMBER,
			CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);
	}

	public static List<PerformanceDay> createPerformanceDays() {
		PerformanceDto dto = createPerformanceRegisterDto();
		return dto.toPerformanceDays(createPerformance());
	}
}
