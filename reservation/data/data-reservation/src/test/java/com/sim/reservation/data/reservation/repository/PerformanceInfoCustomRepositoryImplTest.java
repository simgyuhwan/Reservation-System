package com.sim.reservation.data.reservation.repository;

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

import com.sim.reservation.data.reservation.config.QueryDslTestConfig;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.dto.PerformanceScheduleDto;
import com.sim.reservation.data.reservation.factory.PerformanceInfoFactory;
import com.sim.reservation.data.reservation.factory.PerformanceInfoSearchDtoFactory;
import com.sim.reservation.data.reservation.factory.PerformanceScheduleFactory;
import com.sim.reservation.data.reservation.type.PerformanceType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
class PerformanceInfoCustomRepositoryImplTest {
	public static final String NAME = "바람과 함께 사라지다";
	public static final LocalDate START_DATE = LocalDate.now();
	public static final LocalDate END_DATE = LocalDate.now().plusYears(1);
	public static final String PLACE = "홍대 1극장";

	@Autowired
	private PerformanceInfoRepository performanceInfoRepository;

	@Autowired
	private PerformanceInfoCustomRepository performanceInfoCustomRepository;

	private PerformanceInfo savePerformanceInfo;

	@BeforeEach
	void beforeEach() {
		savePerformanceInfo = performanceInfoRepository.save(createPerformanceInfo(NAME, PLACE, PerformanceType.OTHER));
	}

	@Test
	@DisplayName("기본 값 조회 테스트")
	void defaultLookupTest() {
		//given
		PerformanceInfoSearchDto nullSearchDto = createNullPerformanceInfoSearchDto();

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
			nullSearchDto, createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getSchedules().size()).isEqualTo(savePerformanceInfo.getPerformanceSchedules().size());
		assertThat(result.get(0).getName()).isEqualTo(savePerformanceInfo.getName());
	}

	@Test
	@DisplayName("이름 조회 테스트")
	void nameLookupTest() {
		//given
		String name = "Hong";
		PerformanceInfo performanceInfo = createPerformanceInfo(name, PLACE, PerformanceType.CONCERT);
		performanceInfoRepository.save(performanceInfo);
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByName(name);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getName()).isEqualTo(name);
		assertThat(result.get(0).getType()).isEqualTo(PerformanceType.CONCERT.getName());
	}

	@Test
	@DisplayName("장소 조회 테스트")
	void placeLookupTest() {
		//given
		String place = "홍대 1번 출구";
		PerformanceInfo performanceInfo = createPerformanceInfo(NAME, place, PerformanceType.CONCERT);

		performanceInfoRepository.save(performanceInfo);
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByPlace(place);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getPlace()).isEqualTo(place);
		assertThat(result.get(0).getName()).isEqualTo(NAME);
	}

	@Test
	@DisplayName("타입 조회 테스트")
	void typeLookupTest() {
		//given
		String type = PerformanceType.MUSICAL.getType();
		performanceInfoRepository.save(createPerformanceInfo(NAME, PLACE, PerformanceType.MUSICAL));
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByType(type);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getType()).isEqualTo(PerformanceType.MUSICAL.getName());
		assertThat(result.get(0).getName()).isEqualTo(NAME);
	}

	@Test
	@DisplayName("startDate 를 지난 날짜로 지정하여 조회 테스트")
	void oneYearAgoDateLookupTest() {
		//given
		LocalDate startDate = START_DATE.minusYears(1);
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByStartDate(startDate);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(1);
		PerformanceScheduleDto schedule = result.get(0).getSchedules().get(0);
		assertThat(schedule.getStartDate()).isAfter(startDate);
	}

	@Test
	@DisplayName("endDate 를 지난 날짜로 지정하여 조회 테스트")
	void lastDateLookupTest() {
		//given
		LocalDate endDate = END_DATE.minusYears(1);
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByEndDate(endDate);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
			createDefaultPageable()).getContent();

		//then
		assertThat(result).hasSize(0);
	}

	@Test
	@DisplayName("endDate를 등록된 날짜와 일치할 때 조회 테스트")
	void testLookupWhenEndDateMatches() {
		//given
		PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByEndDate(END_DATE);

		//when
		List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(searchDto,
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

	private PerformanceInfoSearchDto createNullPerformanceInfoSearchDto() {
		return PerformanceInfoSearchDtoFactory.createNullPerformanceSearchDto();
	}

	private PerformanceInfoSearchDto createPerformanceInfoSearchDtoByName(String name) {
		return PerformanceInfoSearchDtoFactory.createPerformanceSearchDtoByName(name);
	}

	private PerformanceInfoSearchDto createPerformanceInfoSearchDtoByPlace(String place) {
		return PerformanceInfoSearchDtoFactory.createPerformanceSearchDtoByPlace(place);
	}

	private PerformanceInfoSearchDto createPerformanceInfoSearchDtoByType(String type) {
		return PerformanceInfoSearchDtoFactory.createPerformanceSearchDtoByType(type);
	}

	private PerformanceInfoSearchDto createPerformanceInfoSearchDtoByStartDate(LocalDate startDate) {
		return PerformanceInfoSearchDtoFactory.createPerformanceSearchDtoByStartDate(startDate);
	}

	private PerformanceInfoSearchDto createPerformanceInfoSearchDtoByEndDate(LocalDate endDate) {
		return PerformanceInfoSearchDtoFactory.createPerformanceSearchDtoByEndDate(endDate);
	}

	private List<PerformanceSchedule> createPerformanceSchedules(PerformanceInfo performanceInfo)  {
		return PerformanceScheduleFactory.createPerformanceSchedules(performanceInfo);
	}

	@NotNull
	private PerformanceInfo createPerformanceInfo(String name, String place, PerformanceType type) {
		PerformanceInfo performanceInfo = PerformanceInfoFactory.createPerformanceInfo(name, place, type);
		List<PerformanceSchedule> performanceSchedules = createPerformanceSchedules(performanceInfo);

		performanceInfo.setPerformanceSchedules(performanceSchedules);
		return performanceInfo;
	}

}