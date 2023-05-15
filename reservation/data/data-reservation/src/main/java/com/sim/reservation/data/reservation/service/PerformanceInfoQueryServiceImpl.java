package com.sim.reservation.data.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.repository.PerformanceInfoCustomRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceInfoQueryServiceImpl.java
 * 공연 정보 조회 서비스
 *
 * @author sgh
 * @since 2023.05.15
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceInfoQueryServiceImpl implements PerformanceInfoQueryService{
    private final PerformanceInfoCustomRepository performanceInfoCustomRepository;

    @Override
    public Page<PerformanceInfoDto> findPerformancesByConditionAndPageable(
        PerformanceInfoSearchDto performanceInfoSearchDto, Pageable pageable) {
        return performanceInfoCustomRepository.selectPerformanceReservation(performanceInfoSearchDto, pageable);
    }
}
