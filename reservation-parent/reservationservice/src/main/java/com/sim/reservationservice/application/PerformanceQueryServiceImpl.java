package com.sim.reservationservice.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservationservice.dao.PerformanceCustomRepository;
import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceQueryServiceImpl.java
 * PerformanceQueryService 구현체
 *
 * @author sgh
 * @since 2023.04.18
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
	private final PerformanceCustomRepository performanceCustomRepository;

	@Override
	public Page<PerformanceInfoDto> selectPerformances(PerformanceSearchDto performanceSearchDto, Pageable pageable) {
		return performanceCustomRepository.selectPerformanceReservation(performanceSearchDto, pageable);
	}
}
