package com.sim.reservationservice.factory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * ReservationTestConstants.java
 * 예약 서비스 태스트 관련 상수
 *
 * @author sgh
 * @since 2023.04.12
 */
public class ReservationTestConstants {
	public static final String START_DATE_KEY = "startDate";
	public static final String END_DATE_KEY = "endDate";
	public static final String NAME_KEY = "name";
	public static final String START_TIME_KEY = "startTime";
	public static final String END_TIME_KEY = "endTime";
	public static final String TYPE_KEY = "type";
	public static final String PLACE_KEY = "place";
	public static final String PAGE_KEY = "page";
	public static final String SIZE_KEY = "size";

	public static final String START_DATE_VALUE = "2045-01-01";
	public static final String END_DATE_VALUE = "2055-01-01";
	public static final String NAME_VALUE = "바람과 함께 사라지다";
	public static final String START_TIME_VALUE = "11:00";
	public static final String END_TIME_VALUE = "14:00";
	public static final String TYPE_VALUE = "MUSICAL";
	public static final String PLACE_VALUE = "홍대 1극장";
	public static final Integer PAGE_VALUE = 0;
	public static final Integer SIZE_VALUE = 10;

	public static final String NAME = "바람과 함께 사라지다";
	public static final String INFO = "공연 소개";
	public static final String TYPE = "MUSICAL";
	public static final LocalDate START_DATE = LocalDate.now();
	public static final LocalDate END_DATE = LocalDate.now().plusYears(1);
	public static final List<LocalTime> START_TIMES = Arrays.asList(LocalTime.of(12, 00),
		LocalTime.of(14, 00));
	public static final String PLACE = "홍대 1극장";
	public static final boolean IS_AVAILABLE = true;
	public static int AUDIENCE_COUNT = 150;
	public static int AVAILABLE_SEATS = 100;
	public static int PRICE = 15000;
	public static String CONTACT_PHONE_NUM = "010-1234-4569";
}
