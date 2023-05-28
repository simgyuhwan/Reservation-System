package com.sim.reservation.boot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.dto.response.PerformanceInfoResponse;
import com.sim.reservation.boot.dto.response.ReservationInfoResponse;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;

/**
 * ReservationSearchService.java
 * 공연 및 예약 조회를 위한 Service
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface ReservationSearchService {
	/**
	 * 예약 가능한 공연 정보 조회
	 */
	Page<PerformanceInfoDto> getAvailablePerformances(PerformanceSearchRequest performanceSearchRequest, Pageable pageable);

	/**
	 * 예약 정보 조회
	 */
	ReservationInfoResponse getReservationInfo(Long reservationId);
}
