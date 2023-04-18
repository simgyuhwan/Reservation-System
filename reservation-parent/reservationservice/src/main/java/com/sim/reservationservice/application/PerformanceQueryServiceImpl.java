package com.sim.reservationservice.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService{

	@Override
	public List<PerformanceInfoDto> selectPerformances(PerformanceSearchDto performanceSearchDto, Pageable pageable) {
		return null;
	}
}
