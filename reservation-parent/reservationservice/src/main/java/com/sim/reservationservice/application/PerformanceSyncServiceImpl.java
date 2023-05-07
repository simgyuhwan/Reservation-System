package com.sim.reservationservice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.client.PerformanceApiClient;
import com.reservation.common.dto.PerformanceDto;
import com.sim.reservationservice.application.mapper.PerformanceInfoMapper;
import com.sim.reservationservice.dao.PerformanceInfoRepository;
import com.sim.reservationservice.domain.PerformanceInfo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 공연 정보를 예약 서비스에 동기화하는 서비스
 *
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceSyncServiceImpl implements PerformanceSyncService{
	private final PerformanceApiClient performanceApiClient;
	private final PerformanceInfoMapper performanceInfoMapper;
	private final PerformanceInfoRepository performanceInfoRepository;

	@CircuitBreaker(name = "getPerformances", fallbackMethod = "fallback")
	@Override
	public boolean requestAndSavePerformanceInfo(Long performanceId) {
		PerformanceDto performanceDto = performanceApiClient.getPerformanceById(performanceId);
		PerformanceInfo performanceInfo = performanceInfoMapper.toEntity(performanceDto);
		performanceInfoRepository.save(performanceInfo);
		return true;
	}

	public boolean fallback(Long performanceId, Throwable ex) {
		log.error("CircuitBreaker is open. Failed to get performances by performanceId : {}", performanceId, ex);
		return false;
	}
}
