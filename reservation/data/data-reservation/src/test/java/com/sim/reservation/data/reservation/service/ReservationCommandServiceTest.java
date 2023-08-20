package com.sim.reservation.data.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.PerformanceScheduleNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SeatLockException;
import com.sim.reservation.data.reservation.error.SoldOutException;
import com.sim.reservation.data.reservation.event.internal.InternalEventPublisher;
import com.sim.reservation.data.reservation.provider.LockProvider;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;
import com.sim.reservation.data.reservation.repository.PerformanceScheduleRepository;
import com.sim.reservation.data.reservation.repository.ReservationRepository;
import com.sim.reservation.data.reservation.type.PerformanceType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * ReservationCommandServiceTest.java 예약 Command Service 테스트
 *
 * @author sgh
 * @since 2023.04.26
 */
@ExtendWith(MockitoExtension.class)
class ReservationCommandServiceTest {

  private static final long PERFORMANCE_ID = 1L;
  private static final long SCHEDULE_ID = 1L;

  @InjectMocks
  private ReservationCommandServiceImpl reservationCommandService;

  @Mock
  private InternalEventPublisher internalEventPublisher;

  @Mock
  private PerformanceInfoRepository performanceInfoRepository;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private PerformanceScheduleRepository performanceScheduleRepository;

  @Mock
  private LockProvider lockProvider;

  @Nested
  @DisplayName("Lock을 얻었을 때")
  class WhenCanGetLock {

    @Test
    @DisplayName("공연 ID와 공연 스케줄 ID, 공연 예약 정보를 통해 예약할 수 있다.")
    void reservationWholesalerSaveConfirmation() throws InterruptedException {
      //given
      getLock();

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(150, true);
      PerformanceInfo performanceInfo = createPerformanceInfo(performanceSchedule, true);
      Reservation expectedReservation = createReservation();

      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(performanceSchedule));
      given(performanceInfoRepository.findById(PERFORMANCE_ID)).willReturn(
          Optional.of(performanceInfo));
      given(reservationRepository.save(any())).willReturn(expectedReservation);

      //when
      ReservationDto reservation = reservationCommandService.createReservation(PERFORMANCE_ID,
          SCHEDULE_ID,
          createReservationDto());

      //then
      verify(reservationRepository).save(any());
      assertThat(reservation.getName()).isEqualTo(expectedReservation.getName());
      assertThat(reservation.getEmail()).isEqualTo(expectedReservation.getEmail());
      assertThat(reservation.getPhoneNum()).isEqualTo(expectedReservation.getPhoneNum());
    }

    @Test
    @DisplayName("등록되지 않은 공연 ID를 예약하려면 예외가 발생한다.")
    void noRegisteredPerformanceInformationException() throws InterruptedException {
      // given
      getLock();

      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(createPerformanceSchedule()));
      given(performanceInfoRepository.findById(PERFORMANCE_ID)).willReturn(Optional.empty());
      ReservationDto reservationRequestDto = createReservationDto();

      // when, then
      assertThatThrownBy(() ->
          reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
              reservationRequestDto)
      ).isInstanceOf(PerformanceInfoNotFoundException.class);
    }

    @Test
    @DisplayName("등록되지 않은 공연 스케줄을 예약하려하면 예외가 발생한다.")
    void exceptionForUnregisteredPerformanceSchedule() {
      // given
      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.empty());
      ReservationDto reservationRequestDto = createReservationDto();

      // when, then
      assertThatThrownBy(() ->
          reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
              reservationRequestDto)
      ).isInstanceOf(PerformanceScheduleNotFoundException.class);
    }

    @Test
    @DisplayName("예약 불가능한 공연을 예약하려고 하면 예외가 발생한다.")
    void unReservablePerformanceException() throws InterruptedException {
      // given
      getLock();

      PerformanceSchedule performanceSchedule = createPerformanceSchedule();
      PerformanceInfo performanceInfo = createPerformanceInfo(performanceSchedule, false);
      ReservationDto reservationDto = createReservationDto();

      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(performanceSchedule));
      given(performanceInfoRepository.findById(PERFORMANCE_ID)).willReturn(
          Optional.of(performanceInfo));

      // when, then
      assertThatThrownBy(() ->
          reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
              reservationDto)
      ).isInstanceOf(ReservationNotPossibleException.class);
    }

    @Test
    @DisplayName("하나 남은 좌석을 예약하면 더이상 예약이 불가능해진다.")
    void verificationOfSoldOutAfterReservation() throws InterruptedException {
      //given
      getLock();

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(1, true);
      PerformanceInfo performanceInfo = createPerformanceInfo(performanceSchedule, true);

      Reservation reservation = createReservation();
      ReservationDto expectedReservation = createReservationDto();

      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(performanceSchedule));
      given(performanceInfoRepository.findById(PERFORMANCE_ID)).willReturn(
          Optional.of(performanceInfo));
      given(reservationRepository.save(any())).willReturn(reservation);

      //when
      reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
          expectedReservation);

      //then
      assertThat(performanceSchedule.isAvailable()).isFalse();
      assertThat(performanceSchedule.getRemainingSeats()).isZero();
    }

    @Test
    @DisplayName("매진된 공연을 예약하려하면 예외가 발생한다.")
    void exceptionForPerformanceSoldOut() {
      // given
      PerformanceSchedule performanceSchedule = createPerformanceSchedule(0, false);
      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(performanceSchedule));
      ReservationDto reservationRequestDto = createReservationDto();

      // when, then
      assertThatThrownBy(
          () -> reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
              reservationRequestDto))
          .isInstanceOf(SoldOutException.class);
    }

    private void getLock() throws InterruptedException {
      given(lockProvider.tryLock(anyString())).willReturn(true);
    }

  }

  @Nested
  @DisplayName("Lock을 얻지 못했을 때")
  class WhenCannotGetLock {

    @Test
    @DisplayName("Lock 획득 실패시 예외가 발생한다.")
    void excludingPerformanceDatesNotInTheSchedule() throws InterruptedException {
      // given
      canTGetLock();

      PerformanceSchedule performanceSchedule = createPerformanceSchedule(1, true);
      given(performanceScheduleRepository.findById(SCHEDULE_ID)).willReturn(
          Optional.of(performanceSchedule));
      ReservationDto reservationRequestDto = createReservationDto();

      assertThatThrownBy(
          () -> reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
              reservationRequestDto))
          .isInstanceOf(SeatLockException.class)
          .hasMessage(ErrorMessage.CANNOT_GET_SEAT_LOCK.getMessage());
    }

    private void canTGetLock() throws InterruptedException {
      given(lockProvider.tryLock(anyString())).willReturn(false);
    }

  }

  private ReservationDto createReservationDto() {
    return ReservationDto.builder()
        .userId("test")
        .name("홍길동")
        .phoneNum("010-1234-4569")
        .email("test@naver.com")
        .isSmsReceiveDenied(true)
        .isEmailReceiveDenied(true)
        .build();
  }

  public PerformanceSchedule createPerformanceSchedule() {
    return PerformanceSchedule.builder()
        .id(1L)
        .performanceInfo(null)
        .startDate(LocalDate.of(2023, 1, 1))
        .endDate(LocalDate.of(2024, 1, 1))
        .startTime(LocalTime.of(12, 0))
        .availableSeats(150)
        .remainingSeats(150)
        .isAvailable(true)
        .build();
  }

  public PerformanceSchedule createPerformanceSchedule(int remainingSeats, boolean isAvailable) {
    return PerformanceSchedule.builder()
        .id(1L)
        .performanceInfo(null)
        .startDate(LocalDate.of(2023, 1, 1))
        .endDate(LocalDate.of(2024, 1, 4))
        .startTime(LocalTime.of(12, 0))
        .availableSeats(150)
        .remainingSeats(remainingSeats)
        .isAvailable(isAvailable)
        .build();
  }

  public PerformanceInfo createPerformanceInfo(
      PerformanceSchedule performanceSchedule, boolean isAvailable) {
    return PerformanceInfo.builder()
        .performanceId(1L)
        .info("공연 소개")
        .name("바람과 함께 사라지다")
        .place("홍대 1극장")
        .type(PerformanceType.MUSICAL)
        .price(15000)
        .isAvailable(isAvailable)
        .performanceSchedules(List.of(performanceSchedule))
        .contactPhoneNum("010-1234-4569")
        .contactPersonName("홍길동")
        .build();
  }

  private Reservation createReservation() {
    return Reservation.builder()
        .id(1L)
        .userId("test")
        .name("홍길동")
        .phoneNum("010-1234-4569")
        .email("test@naver.com")
        .isEmailReceiveDenied(true)
        .isSmsReceiveDenied(true)
        .build();
  }

}