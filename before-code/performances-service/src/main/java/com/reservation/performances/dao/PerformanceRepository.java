package com.reservation.performances.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performances.domain.Performance;

/**
 * PerformanceRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
