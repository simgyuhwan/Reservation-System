package com.sim.reservationservice.factory;

import static com.sim.reservationservice.factory.ReservationTestConstants.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.reservation.common.type.PerformanceType;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.dto.request.PerformanceDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

/**
 * ReservationTestDataFactory.java
 * 예약 서비스 관련 팩토리 클래스
 *
 * @author sgh
 * @since 2023.04.12
 */
public class ReservationTestDataFactory {

	public static List<PerformanceInfoDto> createPerformanceInfoList() {
		return List.of(createPerformanceInfoDto());
	}

	public static Page<PerformanceInfoDto> createPerformanceInfoListForPage() {
		List<PerformanceInfoDto> performanceInfoDtoList = List.of(createPerformanceInfoDto());
		PageRequest pageable = PageRequest.of(1, 15);
		return new PageImpl<>(performanceInfoDtoList,pageable, performanceInfoDtoList.size());
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
			.userId(USER_ID)
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

	public static PerformanceInfo createPerformanceInfo() {
		PerformanceInfo performanceInfo = PerformanceInfo.of(1L, NAME, INFO, PLACE, IS_AVAILABLE, PRICE,
			CONTACT_PHONE_NUM, CONTACT_PERSON_NAME, 1L, PerformanceType.findByType(TYPE), null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedules(performanceInfo));
		return performanceInfo;
	}

	public static PerformanceInfo createPerformanceInfo(String name, String place, PerformanceType type) {
		PerformanceInfo performanceInfo = PerformanceInfo.of(name, INFO, place, IS_AVAILABLE, PRICE, CONTACT_PHONE_NUM,
			CONTACT_PERSON_NAME, 2L, type, null);
		performanceInfo.setPerformanceSchedules(createPerformanceSchedules(performanceInfo));
		return performanceInfo;
	}

	public static List<PerformanceSchedule> createPerformanceSchedules(PerformanceInfo performanceInfo) {
		PerformanceSchedule performanceSchedule1 = PerformanceSchedule.builder()
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
}
