package com.sim.reservation.data.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;

/**
 * PerformanceInfoRepository.java
 *
 * @author sgh
 * @since 2023.04.18
 */
public interface PerformanceInfoRepository extends JpaRepository<PerformanceInfo, Long> {
}
