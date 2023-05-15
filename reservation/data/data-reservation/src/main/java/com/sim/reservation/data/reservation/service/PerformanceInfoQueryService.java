package com.sim.reservation.data.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;

/**
 * PerformanceInfoQueryService.java
 * 공연 정보 조회 서비스
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface PerformanceInfoQueryService {
	Page<PerformanceInfoDto> findPerformancesByConditionAndPageable(PerformanceInfoSearchDto performanceInfoSearchDto, Pageable pageable);
}
