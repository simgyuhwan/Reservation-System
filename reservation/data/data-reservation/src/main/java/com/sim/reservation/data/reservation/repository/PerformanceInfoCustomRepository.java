package com.sim.reservation.data.reservation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;

/**
 * PerformanceInfoCustomRepository.java
 * 공연 QueryDsl 관련 Repository
 *
 * @author sgh
 * @since 2023.04.20
 */
public interface PerformanceInfoCustomRepository {
    Page<PerformanceInfoDto> selectPerformanceReservation(PerformanceInfoSearchDto performanceInfoSearchDto, Pageable pageable);
}
