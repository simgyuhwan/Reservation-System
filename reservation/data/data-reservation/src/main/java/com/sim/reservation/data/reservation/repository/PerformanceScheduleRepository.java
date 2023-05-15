package com.sim.reservation.data.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservation.data.reservation.domain.PerformanceSchedule;

/**
 * PerformanceScheduleRepository.java
 * 공연 스케줄 관련 Repository
 *
 * @author sgh
 * @since 2023.04.28
 */
public interface PerformanceScheduleRepository extends JpaRepository<PerformanceSchedule, Long> {
}
