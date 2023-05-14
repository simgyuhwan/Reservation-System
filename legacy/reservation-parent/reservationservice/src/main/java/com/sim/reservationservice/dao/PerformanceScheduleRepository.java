package com.sim.reservationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservationservice.domain.PerformanceSchedule;

/**
 * PerformanceScheduleRepository.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.04.28
 */
public interface PerformanceScheduleRepository extends JpaRepository<PerformanceSchedule, Long> {
}
