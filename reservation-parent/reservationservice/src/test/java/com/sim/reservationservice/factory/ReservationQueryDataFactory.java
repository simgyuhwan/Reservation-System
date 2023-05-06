package com.sim.reservationservice.factory;

import static com.sim.reservationservice.factory.ReservationQueryConstants.*;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.reservation.common.type.PerformanceTypes;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.dto.request.PerformanceDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

/**
 * ReservationQueryDataFactory.java
 * 예약 서비스 조회 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.04.12
 */
public class ReservationQueryDataFactory {

	public static List<PerformanceInfoDto> createPerformanceInfoList() {
		return List.of(createPerformanceInfoDto());
	}

	public static Page<PerformanceInfoDto> createPerformanceInfoListForPage() {
		List<PerformanceInfoDto> performanceInfoDtoList = List.of(createPerformanceInfoDto());
		PageRequest pageable = PageRequest.of(1, 15);
		return new PageImpl<>(performanceInfoDtoList, pageable, performanceInfoDtoList.size());
	}

	public static PerformanceInfoDto createPerformanceInfoDto() {
		return PerformanceInfoDto.builder()
			.name(NAME)
			.info(INFO)
			.type(TYPE)
			.place(PLACE)
			.isAvailable(IS_AVAILABLE)
			.price(PRICE)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.contactPersonName(CONTACT_PERSON_NAME)
			.build();
	}

	public static PerformanceDto createPerformanceDto() {
		return PerformanceDto.builder()
			.performanceId(1L)
			.memberId(MEMBER_ID)
			.performanceName(NAME)
			.performanceStartDate(START_DATE_VALUE)
			.performanceEndDate(END_DATE_VALUE)
			.performanceInfo(INFO)
			.performanceType(TYPE)
			.contactPhoneNum(CONTACT_PHONE_NUM)
			.performancePlace(PLACE)
			.price(PRICE)
			.audienceCount(AUDIENCE_COUNT)
			.contactPersonName(CONTACT_PERSON_NAME)
			.performanceTimes(START_TIMES_STRING)
			.build();
	}

	public static PerformanceInfo createPerformanceInfoWithOneSeats() {
		PerformanceInfo performanceInfo = createPerformanceInfo();
		List<PerformanceSchedule> schedulesWithOneSeats = createPerformanceSchedulesWithOneSeats(
			performanceInfo);
		performanceInfo.setPerformanceSchedules(schedulesWithOneSeats);
		return performanceInfo;
	}

	public static PerformanceInfo createSoldOutPerformanceInfo() {
		PerformanceInfo performanceInfo = PerformanceInfo.of(1L, NAME, INFO, PLACE, IS_AVAILABLE, PRICE,
			CONTACT_PHONE_NUM, CONTACT_PERSON_NAME, 1L, PerformanceTypes.findByType(TYPE), null);
		performanceInfo.setPerformanceSchedules(List.of(createSoldOutPerformanceSchedule(performanceInfo)));
		return performanceInfo;
	}

	public static PerformanceInfo createPerformanceInfoWithScheduleId() {
		PerformanceInfo performanceInfo = PerformanceInfo.of(1L, NAME, INFO, PLACE, IS_AVAILABLE, PRICE,
			CONTACT_PHONE_NUM, CONTACT_PERSON_NAME, 1L, PerformanceTypes.findByType(TYPE), null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedulesWithId(performanceInfo));
		return performanceInfo;
	}

	public static PerformanceInfo createPerformanceInfo() {
		PerformanceInfo performanceInfo = PerformanceInfo.of(1L, NAME, INFO, PLACE, IS_AVAILABLE, PRICE,
			CONTACT_PHONE_NUM, CONTACT_PERSON_NAME, 1L, PerformanceTypes.findByType(TYPE), null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedules(performanceInfo));
		return performanceInfo;
	}

	public static PerformanceInfo createDisablePerformanceInfo() {
		PerformanceInfo performanceInfo = PerformanceInfo.of(1L, NAME, INFO, PLACE, false, PRICE,
			CONTACT_PHONE_NUM, CONTACT_PERSON_NAME, 1L, PerformanceTypes.findByType(TYPE), null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedulesWithId(performanceInfo));
		return performanceInfo;
	}

	public static PerformanceInfo createPerformanceInfo(String name, String place, PerformanceTypes type) {
		PerformanceInfo performanceInfo = PerformanceInfo.of(name, INFO, place, IS_AVAILABLE, PRICE, CONTACT_PHONE_NUM,
			CONTACT_PERSON_NAME, 2L, type, null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedules(performanceInfo));
		return performanceInfo;
	}

	public static List<PerformanceSchedule> createPerformanceSchedulesWithOneSeats(PerformanceInfo performanceInfo) {
		return List.of(createPerformanceScheduleOneSeat(performanceInfo));
	}

	public static List<PerformanceSchedule> createPerformanceSchedules(PerformanceInfo performanceInfo) {
		PerformanceSchedule performanceSchedule1 =
			PerformanceSchedule.builder()
				.startDate(START_DATE)
				.endDate(END_DATE)
				.startTime(LocalTime.of(11, 00))
				.performanceInfo(performanceInfo)
				.remainingSeats(REMAINING_SEATS)
				.availableSeats(AVAILABLE_SEATS)
				.build();

		PerformanceSchedule performanceSchedule2 = PerformanceSchedule.builder()
			.startDate(START_DATE)
			.endDate(END_DATE)
			.startTime(LocalTime.of(12, 30))
			.performanceInfo(performanceInfo)
			.remainingSeats(REMAINING_SEATS)
			.availableSeats(AVAILABLE_SEATS)
			.build();

		return List.of(performanceSchedule1, performanceSchedule2);
	}

	public static List<PerformanceSchedule> createPerformanceSchedulesWithId(PerformanceInfo performanceInfo) {
		PerformanceSchedule performanceSchedule1 = new PerformanceSchedule(1L, performanceInfo, START_DATE, END_DATE,
			LocalTime.of(11, 00), AVAILABLE_SEATS, REMAINING_SEATS, true);

		PerformanceSchedule performanceSchedule2 = new PerformanceSchedule(2L, performanceInfo, START_DATE, END_DATE,
			LocalTime.of(12, 30), AVAILABLE_SEATS, REMAINING_SEATS, true);

		return List.of(performanceSchedule1, performanceSchedule2);
	}

	public static PerformanceSchedule createPerformanceSchedule(PerformanceInfo performanceInfo) {
		return new PerformanceSchedule(1L, performanceInfo, START_DATE, END_DATE, LocalTime.now(), AVAILABLE_SEATS,
			REMAINING_SEATS, true);
	}

	public static PerformanceSchedule createSoldOutPerformanceSchedule(PerformanceInfo performanceInfo) {
		return new PerformanceSchedule(1L, performanceInfo, START_DATE, END_DATE, LocalTime.now(), AVAILABLE_SEATS,
			REMAINING_SEATS, false);
	}

	public static PerformanceSchedule createPerformanceScheduleOneSeat(PerformanceInfo performanceInfo) {
		return new PerformanceSchedule(1L, performanceInfo, START_DATE, END_DATE, LocalTime.now(), AVAILABLE_SEATS,
			1, true);
	}

}
