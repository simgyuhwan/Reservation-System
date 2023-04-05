package com.reservation.performanceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performanceservice.domain.Performance;

/**
 * PerformanceRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
