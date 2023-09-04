package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.PerformanceScheduleNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SeatLockException;
import com.sim.reservation.data.reservation.error.SoldOutException;
import com.sim.reservation.data.reservation.event.internal.InternalEventPublisher;
import com.sim.reservation.data.reservation.event.payload.ReservationApplyEventPayload;
import com.sim.reservation.data.reservation.event.payload.ReservationCancelEventPayload;
import com.sim.reservation.data.reservation.provider.LockProvider;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;
import com.sim.reservation.data.reservation.repository.PerformanceScheduleRepository;
import com.sim.reservation.data.reservation.repository.ReservationRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ReservationCommandServiceImpl.java 예약 Command Service
 *
 * @author sgh
 * @since 2023.05.15
 */
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {

  private final LockProvider lockProvider;
  private final PerformanceInfoRepository performanceInfoRepository;
  private final PerformanceScheduleRepository scheduleRepository;
  private final ReservationRepository reservationRepository;
  private final InternalEventPublisher internalEventPublisher;

  /**
   * 예약 도메인 생성(예약 신청)
   *
   * @param performanceId  공연 ID
   * @param scheduleId     공연 스케줄 ID
   * @param reservationDto 예약 정보 DTO
   * @return
   */
  @Override
  public ReservationDto createReservation(Long performanceId, Long scheduleId,
      ReservationDto reservationDto) {
    checkReservable(scheduleId);

    PerformanceInfo performanceInfo = performanceInfoRepository.findById(performanceId)
        .orElseThrow(
            () -> new PerformanceInfoNotFoundException(ErrorMessage.PERFORMANCE_INFO_NOT_FOUND,performanceId)
        );

    PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);
    validationReservation(performanceInfo);

    try {
      lockSeat(performanceId, scheduleId);
      reservationSeat(schedule);
      updateReserveAvailability(scheduleId, schedule.isAvailable());

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      unlockSeat(performanceId, scheduleId);
    }

    Reservation reservation = saveReservation(reservationDto,
        schedule);

    publishReservationApplyEvent(reservation);
    return ReservationDto.from(reservation);
  }

  /**
   * 예약 신청 이벤트 발행
   */
  private void publishReservationApplyEvent(Reservation reservation) {
    internalEventPublisher.publishReservationApplyEvent(createReservationApplyEvent(reservation));
  }


  /**
   * 예약 정보 저장
   */
  @NotNull
  private Reservation saveReservation(ReservationDto reservationDto, PerformanceSchedule schedule) {
    Reservation reservation = reservationDto.toEntity(schedule);
    return reservationRepository.save(reservation);
  }

  /**
   * 좌석 예약
   */
  private static void reservationSeat(PerformanceSchedule schedule) {
    schedule.reserveSeat();
  }

  /**
   * 락 해제
   */
  private void unlockSeat(Long performanceId, Long scheduleId) {
    lockProvider.unlock(createKey(performanceId, scheduleId));
  }

  /**
   * 락
   */
  private void lockSeat(Long performanceId, Long scheduleId) throws InterruptedException {
    if (!(lockProvider.tryLock(createKey(performanceId, scheduleId)))) {
      throw new SeatLockException(ErrorMessage.CANNOT_GET_SEAT_LOCK);
    }
  }

  /**
   * 예약 가능 여부 확인
   */
  private void checkReservable(Long scheduleId) {
    boolean isReservable = getReserveAvailability(scheduleId);

    if (!isReservable) {
      throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
    }
  }

  /**
   * 락 키 생성
   */
  @NotNull
  private static String createKey(Long performanceId, Long scheduleId) {
    return "SEAT_LOCK:" + performanceId + ":" + scheduleId;
  }

  /**
   * 예약 신청 삭제
   *
   * @param reservationId 예약 ID
   */
  @Override
  public void cancelReservation(Long reservationId) {
    Reservation reservation = findReservationById(reservationId);
    reservation.cancelReservation();
    internalEventPublisher.publishReservationCancelEvent(
        ReservationCancelEventPayload.from(reservation));
  }


  /**
   * 공연 예약 가능 여부 확인
   *
   * @param performanceInfo 공연 예약 정보
   */
  private void validationReservation(PerformanceInfo performanceInfo) {
    if (!performanceInfo.isAvailable()) {
      throw new ReservationNotPossibleException(ErrorMessage.RESERVATION_NOT_AVAILABLE,
          performanceInfo.getPerformanceId());
    }
  }

  /**
   * 공연 예약 정보에서 스케줄 확인 만약 등록된 공연 시간이 아니면 예외 발생
   */
  private PerformanceSchedule findPerformanceSchedule(PerformanceInfo performanceInfo,
      Long scheduleId) {
    return performanceInfo.findScheduleById(scheduleId)
        .orElseThrow(() -> new NoSuchElementException(
            ErrorMessage.NO_MATCHING_PERFORMANCE_TIMES.getMessage() + scheduleId));
  }

  /**
   * 예약 가능 여부 조회
   */
  @Cacheable(value = "performance-reserve-availability", key = "#scheduleId")
  public boolean getReserveAvailability(Long scheduleId) {
    return scheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new PerformanceScheduleNotFoundException(
            ErrorMessage.PERFORMANCE_SCHEDULE_NOT_FOUND, scheduleId))
        .isAvailable();
  }

  /**
   * 예약 가능 여부 업데이트
   */
  @CachePut(value = "performance-reserve-availability", key = "#scheduleId")
  public Boolean updateReserveAvailability(Long scheduleId, Boolean isAvailable) {
    return isAvailable;
  }

  /**
   * 예약 신청 이벤트 생성
   */
  private ReservationApplyEventPayload createReservationApplyEvent(Reservation reservation) {
    return ReservationApplyEventPayload.from(reservation);
  }

  /**
   * 예약 도메인 조회
   *
   * @param reservationId 예약 ID
   * @return 예약 도메인 객체
   */
  private Reservation findReservationById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new ReservationNotFoundException(ErrorMessage.RESERVATION_NOT_FOUND,
            reservationId));
  }
}
