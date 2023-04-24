package com.sim.reservationservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

/**
 * PerformanceCustomRepository.java
 * 공연 QueryDsl 관련 Repository
 *
 * @author sgh
 * @since 2023.04.20
 */
public interface PerformanceCustomRepository {
    Page<PerformanceInfoDto> selectPerformanceReservation(PerformanceSearchDto performanceSearchDto, Pageable pageable);
}
