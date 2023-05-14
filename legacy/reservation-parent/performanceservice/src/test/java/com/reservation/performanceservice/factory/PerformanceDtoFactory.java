package com.reservation.performanceservice.factory;

import java.util.Set;

import com.reservation.performanceservice.dto.request.PerformanceDto;

public class PerformanceDtoFactory {
	public final static Long MEMBER_ID = 1L;
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

	public static final String PERFORMANCE_BASE_API_URL = "/api/performances";
	public static final String INVALID_REGISTER_VALUE_ERROR_MESSAGE = "공연 등록 값이 올바르지 않습니다.";
	public static final String INVALID_CONTACT_NUMBER_ERROR_MESSAGE = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx";
	public static final String PERFORMANCE_INFO_LENGTH_EXCEEDED_ERROR_MESSAGE = "공연 정보는 최대 255자입니다.";
	public static final String INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'";
	public static final String INCORRECT_PERFORMANCE_TIME_ERROR_MESSAGE = "공연 시간 형식이 잘못되었습니다. ex) '15:45'";
	public static final Integer PERFORMANCE_MAXIMUM_COUNT = 255;
	public static final String MIN_AUDIENCE_ERROR_MESSAGE = "관객 수는 반드시 10명 이상이어야 합니다.";

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

	public static PerformanceDto createPerformanceDto(Long memberId, String performanceName,String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceInfo, String performancePlace) {
		return PerformanceDto.builder()
			.memberId(memberId)
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
			.memberId(MEMBER_ID)
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

}
