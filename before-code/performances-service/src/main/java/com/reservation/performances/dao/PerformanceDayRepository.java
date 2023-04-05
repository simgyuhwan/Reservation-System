package com.reservation.performances.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performances.domain.PerformanceDay;

/**
 * PerformanceDayRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceDayRepository extends JpaRepository<PerformanceDay, Long> {
}
