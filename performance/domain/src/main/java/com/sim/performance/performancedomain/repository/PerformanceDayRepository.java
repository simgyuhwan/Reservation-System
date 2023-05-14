package com.sim.performance.performancedomain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;

/**
 * PerformanceDayRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceDayRepository extends JpaRepository<PerformanceDay, Long> {
	List<PerformanceDay> findByPerformance(Performance performance);
}
