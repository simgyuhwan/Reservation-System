package com.sim.reservationservice.factory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservationCommandConstants.java
 * 예약 서비스 command 관련 상수 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
public class ReservationCommandConstants {
	public static String USER_ID = "user1";
	public static String NAME = "홍길동";
	public static String PHONE_NUM = "010-8884-2133";
	public static String EMAIL = "test@naver.com";
	public static boolean IS_EMAIL_RECEIVE_DENIED = true;
	public static boolean IS_SNS_RECEIVE_DENIED = true;

	public static String PERFORMANCE_NAME = "나는 전설이다";
	public static LocalDate PERFORMANCE_DATE = LocalDate.now();
	public static LocalTime PERFORMANCE_TIME = LocalTime.now();
}
