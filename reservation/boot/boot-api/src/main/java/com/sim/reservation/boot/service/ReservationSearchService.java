package com.sim.reservation.boot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.dto.response.PerformanceInfoResponse;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;

/**
 * ReservationSearchService.java
 * 공연 및 예약 조회를 위한 Service
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface ReservationSearchService {
	Page<PerformanceInfoDto> getAvailablePerformances(PerformanceSearchRequest performanceSearchRequest, Pageable pageable);
}
