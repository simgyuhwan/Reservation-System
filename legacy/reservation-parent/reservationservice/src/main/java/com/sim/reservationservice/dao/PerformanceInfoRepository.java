package com.sim.reservationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservationservice.domain.PerformanceInfo;

/**
 * PerformanceInfoRepository.java
 *
 * @author sgh
 * @since 2023.04.18
 */
public interface PerformanceInfoRepository extends JpaRepository<PerformanceInfo, Long> {
}
