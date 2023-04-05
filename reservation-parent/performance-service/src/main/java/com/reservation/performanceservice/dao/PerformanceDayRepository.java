package com.reservation.performanceservice.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;

/**
 * PerformanceDayRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceDayRepository extends JpaRepository<PerformanceDay, Long> {
	Optional<PerformanceDay> findByPerformance(Performance performance);
}
