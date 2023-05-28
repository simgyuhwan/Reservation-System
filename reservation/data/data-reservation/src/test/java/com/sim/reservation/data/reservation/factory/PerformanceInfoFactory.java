package com.sim.reservation.data.reservation.factory;

import java.util.ArrayList;
import java.util.List;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.type.PerformanceType;

/**
 * PerformanceInfoFactory.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.15
 */
public class PerformanceInfoFactory {
	public static final Long PERFORMANCE_ID = 1L;
	public static final Long PERFORMANCE_INFO_ID = 1L;
	public static final String NAME = "바람과 함께 사라지다";
	public static final String INFO = "공연 소개";
	public static final PerformanceType TYPE = PerformanceType.MUSICAL;

	public static final String PLACE = "홍대 1극장";
	public static final boolean IS_AVAILABLE = true;
	public static final int PRICE = 15000;
	public static final String CONTACT_PHONE_NUM = "010-1234-4569";
	public static final String CONTACT_PERSON_NAME = "홍길동";

	public static PerformanceInfo createPerformanceInfo(PerformanceSchedule performanceSchedule) {
		return PerformanceInfo.builder()
			.performanceId(PERFORMANCE_ID)
			.info(INFO)
			.name(NAME)
			.place(PLACE)
			.type(TYPE)
			.price(PRICE)
			.isAvailable(IS_AVAILABLE)
			.performanceSchedules(List.of(performanceSchedule))
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}

	public static PerformanceInfo createPerformanceInfoWithId(PerformanceSchedule performanceSchedule) {
		return PerformanceInfo.builder()
			.performanceId(PERFORMANCE_ID)
			.info(INFO)
			.name(NAME)
			.place(PLACE)
			.type(TYPE)
			.price(PRICE)
			.isAvailable(IS_AVAILABLE)
			.performanceSchedules(List.of(performanceSchedule))
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}


	public static PerformanceInfo createPerformanceInfo(String name, String place, PerformanceType type) {
		return PerformanceInfo.builder()
			.performanceId(2L)
			.info(INFO)
			.name(name)
			.place(place)
			.type(type)
			.price(PRICE)
			.isAvailable(IS_AVAILABLE)
			.performanceSchedules(new ArrayList<>())
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}

	public static PerformanceInfo createDisablePerformanceInfo(List<PerformanceSchedule> performanceSchedules) {
		return PerformanceInfo.builder()
			.performanceId(PERFORMANCE_ID)
			.info(INFO)
			.name(NAME)
			.place(PLACE)
			.type(TYPE)
			.price(PRICE)
			.isAvailable(false)
			.performanceSchedules(performanceSchedules)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}

	public static PerformanceInfo createPerformanceInfoWithSchedule() {
		PerformanceInfo performanceInfo = createPerformanceInfoNotSchedule();

		List<PerformanceSchedule> performanceSchedules = PerformanceScheduleFactory.createPerformanceSchedules(
			performanceInfo);

		performanceInfo.setPerformanceSchedules(performanceSchedules);
		return performanceInfo;
	}

	public static PerformanceInfo createPerformanceInfoWithOneSeats() {
		PerformanceInfo performanceInfo = createPerformanceInfoNotSchedule();

		List<PerformanceSchedule> performanceSchedulesOneSeats = PerformanceScheduleFactory.createPerformanceSchedulesOneSeats(
			performanceInfo);

		performanceInfo.setPerformanceSchedules(performanceSchedulesOneSeats);
		return performanceInfo;
	}

	private static PerformanceInfo createPerformanceInfoNotSchedule() {
		return PerformanceInfo.builder()
			.performanceId(PERFORMANCE_ID)
			.info(INFO)
			.name(NAME)
			.place(PLACE)
			.type(TYPE)
			.price(PRICE)
			.isAvailable(true)
			.performanceSchedules(new ArrayList<>())
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}
}
