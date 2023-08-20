package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.InternalException;
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
    Boolean isReservable = getReserveAvailability(scheduleId);

    if (!isReservable) {
      throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
    }

    String key = createKey(performanceId, scheduleId);

    try {
      if (!(lockProvider.tryLock(key))) {
        throw new SeatLockException(ErrorMessage.CANNOT_GET_SEAT_LOCK);
      }

      PerformanceInfo performanceInfo = findPerformanceById(performanceId);
      PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

      validationReservation(performanceInfo);
      schedule.reserveSeat();

      updateReserveAvailability(scheduleId, schedule.isAvailable());

      Reservation reservation = reservationRepository.save(
          Reservation.of(reservationDto, schedule));

      internalEventPublisher.publishReservationApplyEvent(createReservationApplyEvent(reservation));
      return ReservationDto.from(reservation);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new InternalException(e);
    } finally {
      lockProvider.unlock(key);
    }

//    return lockProvider.tryLockAndExecute(key, ErrorMessage.CANNOT_GET_SEAT_LOCK, () -> {
//      PerformanceInfo performanceInfo = findPerformanceById(performanceId);
//      PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);
//
//      validationReservation(performanceInfo);
//      schedule.reserveSeat();
//
//      updateReserveAvailability(scheduleId, schedule.isAvailable());
//
//      Reservation reservation = reservationRepository.save(
//          Reservation.of(reservationDto, schedule));
//
//      internalEventPublisher.publishReservationApplyEvent(createReservationApplyEvent(reservation));
//      return ReservationDto.from(reservation);
//    });
  }

  @NotNull
  private static String createKey(Long performanceId, Long scheduleId) {
    return performanceId + ":" + scheduleId;
  }

  /**
   * 예약 신청 삭제
   *
   * @param reservationId 예약 ID
   */
  @Override
  public void deleteReservation(Long reservationId) {
    Reservation reservation = findReservationById(reservationId);
    reservation.deleteReservation();
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
   * 공연 정보 조회
   */
  private PerformanceInfo findPerformanceById(Long performanceId) {
    return performanceInfoRepository.findById(performanceId)
        .orElseThrow(
            () -> new PerformanceInfoNotFoundException(ErrorMessage.PERFORMANCE_INFO_NOT_FOUND,
                performanceId));
  }

  /**
   * 예약 가능 여부 조회
   */
  @Cacheable(value = "performance-reserve-availability", key = "#scheduleId")
  public Boolean getReserveAvailability(Long scheduleId) {
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
