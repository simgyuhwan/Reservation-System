package com.sim.reservationservice.application;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.error.ErrorMessage;
import com.sim.reservationservice.dao.PerformanceInfoRepository;
import com.sim.reservationservice.dao.PerformanceScheduleRepository;
import com.sim.reservationservice.dao.ReservationRepository;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.domain.Reservation;
import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;
import com.sim.reservationservice.error.PerformanceInfoNotFoundException;
import com.sim.reservationservice.error.PerformanceScheduleNotFoundException;
import com.sim.reservationservice.error.ReservationNotPossibleException;
import com.sim.reservationservice.error.SoldOutException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * ReservationCommandServiceImpl.java
 * 예약 Command 구현 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {
	private static final String SEAT_LOCK = "seat_lock";
	private static final int WAIT_TIME = 1;
	private static final int LEASE_TIME = 2;

	private final PerformanceInfoRepository performanceInfoRepository;
	private final ReservationRepository reservationRepository;
	private final PerformanceScheduleRepository performanceScheduleRepository;
	private final RedissonClient redissonClient;

	@Override
	public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
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
			return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
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
				String.format("내부에 일치하는 performanceSchedule이 없습니다. performanceScheduleId : %s", scheduleId)));
	}

	private PerformanceInfo findPerformanceById(Long performanceId) {
		return performanceInfoRepository.findById(performanceId)
			.orElseThrow(() -> new PerformanceInfoNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
	}

	@Cacheable(value = "performance-reserve-availability", key = "#scheduleId")
	public Boolean getReserveAvailability(Long scheduleId) {
		return performanceScheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new PerformanceScheduleNotFoundException(ErrorMessage.PERFORMANCE_SCHEDULE_NOT_FOUND, scheduleId))
			.isAvailable();

	}

	@CachePut(value = "performance-reserve-availability", key = "#scheduleId")
	public Boolean updateReserveAvailability(Long scheduleId, Boolean isAvailable) {
		return isAvailable;
	}
}

