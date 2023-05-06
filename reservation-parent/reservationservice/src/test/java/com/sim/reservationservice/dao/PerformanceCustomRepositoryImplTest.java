package com.sim.reservationservice.dao;

import static com.sim.reservationservice.factory.ReservationQueryConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.type.PerformanceTypes;
import com.sim.reservationservice.config.QueryDslTestConfig;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;
import com.sim.reservationservice.dto.response.PerformanceScheduleDto;
import com.sim.reservationservice.factory.ReservationQueryDataFactory;

/**
 * PerformanceCustomRepositoryImplTest.java
 * QueryDsl Repository Test
 *
 * @author sgh
 * @since 2023.04.24
 */
@DataJpaTest
@Transactional(readOnly = true)
@Import(QueryDslTestConfig.class)
class PerformanceCustomRepositoryImplTest {

	@Autowired
	private PerformanceInfoRepository performanceInfoRepository;

	@Autowired
	private PerformanceCustomRepository performanceCustomRepository;

	private PerformanceInfo savePerformanceInfo;

	@BeforeEach
	void beforeEach() {
		savePerformanceInfo = performanceInfoRepository.save(createPerformanceInfo());
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : 기본 값 조회 테스트")
	void defaultLookupTest() {
		//given
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder().build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(
			searchDto, createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getSchedules().size()).isEqualTo(savePerformanceInfo.getPerformanceSchedules().size());
		assertThat(result.get(0).getName()).isEqualTo(savePerformanceInfo.getName());
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : 이름 조회 테스트")
	void nameLookupTest() {
		//given
		String searchName = "Hong";
		performanceInfoRepository.save(createPerformanceInfo(searchName, PLACE, PerformanceTypes.CONCERT));
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.name(searchName).build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getName()).isEqualTo(searchName);
		assertThat(result.get(0).getType()).isEqualTo(PerformanceTypes.CONCERT.getName());
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : 장소 조회 테스트")
	void placeLookupTest() {
		//given
		String searchPlace = "홍대 1번 출구";
		performanceInfoRepository.save(createPerformanceInfo(NAME, searchPlace, PerformanceTypes.CONCERT));
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.place(searchPlace)
			.build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getPlace()).isEqualTo(searchPlace);
		assertThat(result.get(0).getName()).isEqualTo(NAME);
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : 타입 조회 테스트")
	void typeLookupTest() {
		//given
		String searchType = "CONCERT";
		performanceInfoRepository.save(createPerformanceInfo(NAME, PLACE, PerformanceTypes.CONCERT));
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.type(searchType)
			.build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getType()).isEqualTo(PerformanceTypes.CONCERT.getName());
		assertThat(result.get(0).getName()).isEqualTo(NAME);
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : startDate 를 지난 날짜로 지정하여 조회 테스트")
	void oneYearAgoDateLookupTest() {
		//given
		LocalDate lastYear = START_DATE.minusYears(1);
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.startDate(lastYear)
			.build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		PerformanceScheduleDto schedule = result.get(0).getSchedules().get(0);
		assertThat(schedule.getStartDate()).isAfter(lastYear);
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : endDate 를 지난 날짜로 지정하여 조회 테스트")
	void lastDateLookupTest() {
		//given
		LocalDate lastYear = END_DATE.minusYears(1);
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.endDate(lastYear)
			.build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(0);
	}

	@Test
	@DisplayName("공연 예약 현황 조회 : endDate를 등록된 날짜와 일치할 때 조회 테스트")
	void testLookupWhenEndDateMatches() {
		//given
		PerformanceSearchDto searchDto = PerformanceSearchDto.builder()
			.endDate(END_DATE)
			.build();

		//when
		List<PerformanceInfoDto> result = performanceCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		PerformanceScheduleDto schedule = result.get(0).getSchedules().get(0);
		assertThat(schedule.getEndDate()).isEqualTo(END_DATE);
	}

	@NotNull
	private PageRequest createDefaultPageable() {
		return PageRequest.of(0, 15);
	}

	private PerformanceInfo createPerformanceInfo() {
		return ReservationQueryDataFactory.createPerformanceInfo();
	}

	private PerformanceInfo createPerformanceInfo(String name, String place, PerformanceTypes type) {
		return ReservationQueryDataFactory.createPerformanceInfo(name, place, type);
	}
}