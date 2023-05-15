package com.sim.reservation.data.reservation.service;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.PerformanceScheduleNotFoundException;
import com.sim.reservation.data.reservation.error.InternalException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SoldOutException;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;
import com.sim.reservation.data.reservation.repository.PerformanceScheduleRepository;
import com.sim.reservation.data.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationCommandServiceImpl.java
 * 예약 Command Service
 *
 * @author sgh
 * @since 2023.05.15
 */
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService{
    private static final String SEAT_LOCK = "seat_lock";
    private static final int WAIT_TIME = 1;
    private static final int LEASE_TIME = 2;

    private final PerformanceInfoRepository performanceInfoRepository;
    private final PerformanceScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;
    private final RedissonClient redissonClient;

    @Override
    public void createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
        Boolean isReservable = getReserveAvailability(scheduleId);

        if (!isReservable) {
            throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
        }

        RLock lock = redissonClient.getLock(SEAT_LOCK);

        try {
            if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
                throw new RuntimeException("Seat Lock 획득 실패");
            }

            PerformanceInfo performanceInfo = findPerformanceById(performanceId);
            PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

            validationReservation(performanceInfo);
            schedule.reserveSeat();

            updateReserveAvailability(scheduleId, schedule.isAvailable());

            Reservation reservation = reservationRepository.save(Reservation.of(reservationDto, schedule));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new InternalException(e);
        } finally {
            lock.unlock();
        }
    }
    private void validationReservation(PerformanceInfo performanceInfo) {
        if (!performanceInfo.isAvailable()) {
            throw new ReservationNotPossibleException(ErrorMessage.RESERVATION_NOT_AVAILABLE,
                performanceInfo.getPerformanceId());
        }
    }

    private PerformanceSchedule findPerformanceSchedule(PerformanceInfo performanceInfo, Long scheduleId) {
        return performanceInfo.findScheduleById(scheduleId)
            .orElseThrow(() -> new NoSuchElementException(
                ErrorMessage.NO_MATCHING_PERFORMANCE_TIMES.getMessage() + scheduleId));
    }

    private PerformanceInfo findPerformanceById(Long performanceId) {
        return performanceInfoRepository.findById(performanceId)
            .orElseThrow(() -> new PerformanceInfoNotFoundException(ErrorMessage.PERFORMANCE_INFO_NOT_FOUND, performanceId));
    }

    @Cacheable(value = "performance-reserve-availability", key = "#scheduleId")
    public Boolean getReserveAvailability(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new PerformanceScheduleNotFoundException(ErrorMessage.PERFORMANCE_SCHEDULE_NOT_FOUND, scheduleId))
            .isAvailable();
    }

    @CachePut(value = "performance-reserve-availability", key = "#scheduleId")
    public Boolean updateReserveAvailability(Long scheduleId, Boolean isAvailable) {
        return isAvailable;
    }
}
