package com.sim.reservation.boot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.dto.response.ReservationInfoResponse;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.dto.ReservationInfo;
import com.sim.reservation.data.reservation.service.PerformanceInfoQueryService;
import com.sim.reservation.data.reservation.service.ReservationQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationSearchServiceImpl.java
 * 공연 및 예약 조회를 위한 Service
 *
 * @author sgh
 * @since 2023.05.15
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationSearchServiceImpl implements ReservationSearchService{
    private final PerformanceInfoQueryService performanceInfoQueryService;
    private final ReservationQueryService reservationQueryService;

    /**
     * 예약 가능한 공연 정보 조회
     */
    @Override
    public Page<PerformanceInfoDto> getAvailablePerformances(PerformanceSearchRequest performanceSearchRequest,
        Pageable pageable) {
        PerformanceInfoSearchDto performanceInfoSearchDto = performanceSearchRequest.toSearchDto();
        return performanceInfoQueryService.findPerformancesByConditionAndPageable(performanceInfoSearchDto, pageable);
    }

    /**
     * 예약 정보 조회
     */
    @Override
    public ReservationInfoResponse getReservationInfo(Long reservationId) {
        ReservationInfo reservationInfo = reservationQueryService.findReservationInfoById(reservationId);
        return ReservationInfoResponse.from(reservationInfo);
    }
}
