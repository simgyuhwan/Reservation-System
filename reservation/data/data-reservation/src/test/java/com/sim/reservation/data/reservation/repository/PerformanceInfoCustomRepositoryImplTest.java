package com.sim.reservation.data.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.support.RepositoryTestSupport;
import com.sim.reservation.data.reservation.type.PerformanceType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

/**
 * PerformanceCustomRepositoryImplTest.java QueryDsl Repository Test
 *
 * @author sgh
 * @since 2023.04.24
 */
class PerformanceInfoCustomRepositoryImplTest extends RepositoryTestSupport {

  public static final LocalDate START_DATE = LocalDate.of(2023, 01, 01);
  public static final LocalDate END_DATE = LocalDate.of(2023, 01, 01).plusYears(1);
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

  @AfterEach
  void tearDown() {
    performanceScheduleRepository.deleteAllInBatch();
    performanceInfoRepository.deleteAllInBatch();
  }

  @Nested
  @DisplayName("조회 조건이 비어있을 때")
  class WhenNoCondition {

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
  class WhenCondition {

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

    static Stream<Arguments> rangeThatCanBeSearched() {
      return Stream.of(
          Arguments.of(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1)),
          Arguments.of(LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1)),
          Arguments.of(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1)),
          Arguments.of(LocalDate.of(2022, 12, 31), LocalDate.of(2024, 1, 1)),
          Arguments.of(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 2)),
          Arguments.of(LocalDate.of(2023, 1, 1), null),
          Arguments.of(null, LocalDate.of(2024, 1, 1))
      );
    }

    @ParameterizedTest
    @MethodSource("rangeThatCanBeSearched")
    @DisplayName("조회하려는 날짜 범위 안에 공연 일정이 포함되어 있으면 조회가 가능하다.")
    void thePerformanceScheduleIsIncludedInTheSearchRange(LocalDate searchStartDate,
        LocalDate searchEndDate) {
      //given
      LocalDate performanceStartDate = LocalDate.of(2023, 1, 1);
      LocalDate performanceEndDate = LocalDate.of(2024, 1, 1);
      LocalTime performanceStartTime = LocalTime.of(12, 0);

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


    static Stream<Arguments> rangeThatCannotBeSearched() {
      return Stream.of(
          Arguments.of(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31)),
          Arguments.of(LocalDate.of(2024, 1, 2), LocalDate.of(2024, 1, 3)),
          Arguments.of(LocalDate.of(2024, 1, 2), null),
          Arguments.of(null, LocalDate.of(2022, 12, 31))
      );
    }

    @ParameterizedTest
    @MethodSource("rangeThatCannotBeSearched")
    @DisplayName("조회하려는 날짜 범위 안에 공연 일정이 포함되어 있지 않으면 조회가 불가능하다.")
    void ifItIsNotInTheSearchRangeTheSearchFails(LocalDate searchStartDate,
        LocalDate searchEndDate) {
      //given
      LocalDate performanceStartDate = LocalDate.of(2023, 1, 1);
      LocalDate performanceEndDate = LocalDate.of(2024, 1, 1);
      LocalTime performanceStartTime = LocalTime.of(12, 0);

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

    @Test
    @DisplayName("공연 시간으로 일치하는 공연 조회가 가능하다.")
    void viewableByPerformanceTime() {
      //given
      LocalDate performanceStartDate = LocalDate.of(2023, 1, 1);
      LocalDate performanceEndDate = LocalDate.of(2024, 1, 1);
      LocalTime performanceStartTime = LocalTime.of(12, 0);
      LocalTime searchStartTime = LocalTime.of(12, 0);

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(performanceStartDate,
          performanceEndDate, performanceStartTime);
      createPerformanceInfo(NAME, TYPE, performanceSchedule);

      PerformanceInfoSearchDto searchDto = PerformanceInfoSearchDto.builder()
          .startTime(searchStartTime)
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
  }

  @NotNull
  private PageRequest createDefaultPageable() {
    return PageRequest.of(0, 15);
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