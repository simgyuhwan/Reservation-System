package com.sim.reservation.data.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.sim.reservation.data.reservation.config.QueryDslTestConfig;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.dto.PerformanceScheduleDto;
import com.sim.reservation.data.reservation.factory.PerformanceInfoSearchDtoFactory;
import com.sim.reservation.data.reservation.type.PerformanceType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

/**
 * PerformanceCustomRepositoryImplTest.java QueryDsl Repository Test
 *
 * @author sgh
 * @since 2023.04.24
 */
@DataJpaTest
@Import(QueryDslTestConfig.class)
class PerformanceInfoCustomRepositoryImplTest {

  public static final LocalDate START_DATE = LocalDate.of(2023, 01, 01);
  public static final LocalDate END_DATE = LocalDate.of(2023, 01, 01).plusYears(1);
  private static final Long PERFORMANCE_ID = 1L;
  private static final Long PERFORMANCE_INFO_ID = 1L;
  private static final String NAME = "바람과 함께 사라지다";
  private static final String INFO = "공연 소개";
  private static final PerformanceType TYPE = PerformanceType.MUSICAL;

  private static final String PLACE = "홍대 1극장";
  private static final boolean IS_AVAILABLE = true;
  private static final int PRICE = 15000;
  private static final String CONTACT_PHONE_NUM = "010-1234-4569";
  private static final String CONTACT_PERSON_NAME = "홍길동";

  @Autowired
  private PerformanceInfoRepository performanceInfoRepository;

  @Autowired
  private PerformanceScheduleRepository performanceScheduleRepository;

  @Autowired
  private PerformanceInfoCustomRepository performanceInfoCustomRepository;

  private PerformanceInfo savePerformanceInfo;

//  @BeforeEach
//  void beforeEach() {
//    savePerformanceInfo = performanceInfoRepository.save(
//        createPerformanceInfo(NAME, PLACE, PerformanceType.OTHER));
//  }


  @AfterEach
  void tearDown() {
    performanceScheduleRepository.deleteAllInBatch();
    performanceInfoRepository.deleteAllInBatch();
  }

  @Nested
  @DisplayName("조회 조건이 비어있을 때")
  class whenNoCondition {

    @Test
    @DisplayName("공연 정보에 공연 스케줄이 없으면 조회가 되지 않는다.")
    void cannotBeSearchedNoPerformanceSchedule() {
      //given
      createPerformanceInfoNonSchedules(NAME, TYPE);
      PerformanceInfoSearchDto noCondition = PerformanceInfoSearchDto.builder().build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          noCondition, createDefaultPageable()).getContent();

      //then
      assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("공연 정보에 공연 스케줄이 있으면 조회가 가능하다.")
    void canBeSearchedPerformanceSchedule() {
      //given
      createPerformanceInfo(NAME, TYPE, createPerformanceSchedule());
      PerformanceInfoSearchDto noCondition = PerformanceInfoSearchDto.builder().build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          noCondition, createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("name", "type")
          .containsExactlyInAnyOrder(
              tuple(NAME, TYPE.getName())
          );
    }
  }

  @Nested
  @DisplayName("조회 조건이 있을 때")
  class whenCondition {

    @Test
    @DisplayName("이름을 조건으로 공연정보를 조회할 수 있다.")
    void nameLookupTest() {
      //given
      String searchName = "Hong";

      createPerformanceInfo(searchName, PLACE, PerformanceType.CONCERT);
      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .name(searchName)
          .build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("name")
          .contains(searchName);
    }

    @Test
    @DisplayName("장소를 조건으로 공연 정보를 조회할 수 있다.")
    void placeLookupTest() {
      //given
      String searchPlace = "홍대 1번 출구";
      createPerformanceInfo(NAME, searchPlace, PerformanceType.CONCERT);

      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .place(searchPlace)
          .build();
      ;

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("place")
          .contains(searchPlace);
    }

    @Test
    @DisplayName("공연 타입을 조건으로 공연을 조회할 수 있다.")
    void typeLookupTest() {
      //given
      String searchType = PerformanceType.MUSICAL.getType();

      createPerformanceInfo(NAME, PLACE, PerformanceType.MUSICAL);
      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .type(searchType)
          .build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("type")
          .contains(PerformanceType.MUSICAL.getName());
    }

    @Test
    @DisplayName("공연날짜가 조회하려는 날짜 사이에 없으면 조회가 불가능하다.")
    void ifItIsNotInTheSearchRangeTheSearchFails() {
      //given
      LocalDate performanceStartDate = LocalDate.of(2023, 1, 1);
      LocalDate performanceEndDate = performanceStartDate.plusYears(1);
      LocalTime performanceStartTime = LocalTime.of(12, 0);

      LocalDate searchStartDate = LocalDate.of(2022, 1, 1);
      LocalDate searchEndDate = LocalDate.of(2022, 12, 31);

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(performanceStartDate,
          performanceEndDate, performanceStartTime);
      createPerformanceInfo(NAME, TYPE, performanceSchedule);

      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .startDate(searchStartDate)
          .endDate(searchEndDate)
          .build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).isEmpty();
    }


    /**
     * 수정해야할 곳
     */
    @Disabled
    @Test
    @DisplayName("공연 시작날짜가 조회하려는 조회 날짜 범위 안에 있으면 조회가 가능하다.")
    void ifTheSearchDateIsBeforeItCanBeSearched() {
      //given
      LocalDate performanceStartDate = LocalDate.of(2023, 1, 1);
      LocalDate performanceEndDate = performanceStartDate.plusYears(1);
      LocalTime performanceStartTime = LocalTime.of(12, 0);

      LocalDate searchStartDate = performanceStartDate.plusDays(1);
      LocalDate searchEndDate = LocalDate.of(2023, 12, 31);

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(performanceStartDate,
          performanceEndDate, performanceStartTime);
      createPerformanceInfo(NAME, TYPE, performanceSchedule);

      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .startDate(searchStartDate)
          .endDate(searchEndDate)
          .build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("name", "type")
          .containsExactlyInAnyOrder(
              tuple(NAME, TYPE.getName())
          );
    }

    @Test
    @DisplayName("endDate 를 지난 날짜로 지정하여 조회 테스트")
    void lastDateLookupTest() {
      //given
      LocalDate startDate = LocalDate.of(2023, 1, 1);
      LocalDate endDate = startDate.plusYears(1);
      LocalTime startTime = LocalTime.of(12, 0);

      LocalDate beforeThePerformanceDate = startDate.minusDays(1);

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(startDate,
          endDate, startTime);
      createPerformanceInfo(NAME, TYPE, performanceSchedule);

      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .startDate(beforeThePerformanceDate)
          .build();

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1)
          .extracting("name", "type")
          .containsExactlyInAnyOrder(
              tuple(NAME, TYPE.getName())
          );
    }

    @Test
    @DisplayName("endDate를 등록된 날짜와 일치할 때 조회 테스트")
    void testLookupWhenEndDateMatches() {
      //given
      PerformanceInfoSearchDto searchDto = createPerformanceInfoSearchDtoByEndDate(END_DATE);

      //when
      List<PerformanceInfoDto> result = performanceInfoCustomRepository.selectPerformanceReservation(
          searchDto,
          createDefaultPageable()).getContent();

      //then
      assertThat(result).hasSize(1);
      PerformanceScheduleDto schedule = result.get(0).getSchedules().get(0);
      assertThat(schedule.getEndDate()).isEqualTo(END_DATE);
    }
  }


  @NotNull
  private PageRequest createDefaultPageable() {
    return PageRequest.of(0, 15);
  }

  private PerformanceInfoSearchDto createNullPerformanceInfoSearchDto() {
    return PerformanceInfoSearchDto.builder().build();
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


  private void createPerformanceInfo(String name, String place, PerformanceType type) {
    PerformanceInfo performanceInfo = PerformanceInfo.builder()
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

    PerformanceSchedule performanceSchedule = createPerformanceSchedule();

    performanceInfo.addPerformanceSchedule(performanceSchedule);
    performanceInfoRepository.save(performanceInfo);
  }

  private void createPerformanceInfo(String name, PerformanceType type,
      PerformanceSchedule schedule) {
    PerformanceInfo performanceInfo = PerformanceInfo.builder()
        .performanceId(2L)
        .info(INFO)
        .name(name)
        .place(PLACE)
        .type(type)
        .price(PRICE)
        .isAvailable(IS_AVAILABLE)
        .performanceSchedules(new ArrayList<>())
        .contactPhoneNum(CONTACT_PHONE_NUM)
        .contactPersonName(CONTACT_PERSON_NAME)
        .build();

    performanceInfo.addPerformanceSchedule(schedule);
    performanceInfoRepository.save(performanceInfo);
  }

  private void createPerformanceInfoNonSchedules(String name,
      PerformanceType type) {
    PerformanceInfo performanceInfo = PerformanceInfo.builder()
        .performanceId(2L)
        .info(INFO)
        .name(name)
        .place(PLACE)
        .type(type)
        .price(PRICE)
        .isAvailable(IS_AVAILABLE)
        .contactPhoneNum(CONTACT_PHONE_NUM)
        .contactPersonName(CONTACT_PERSON_NAME)
        .build();

    performanceInfoRepository.save(performanceInfo);
  }

  private PerformanceSchedule createPerformanceSchedule() {
    return createPerformanceSchedule(START_DATE, END_DATE, LocalTime.now());
  }

  private PerformanceSchedule createPerformanceSchedule(LocalDate startDate, LocalDate endDate,
      LocalTime startTime) {
    return PerformanceSchedule.builder()
        .startDate(startDate)
        .endDate(endDate)
        .startTime(startTime)
        .availableSeats(150)
        .remainingSeats(150)
        .isAvailable(IS_AVAILABLE)
        .build();
  }

}