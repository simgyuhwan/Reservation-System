package com.sim.reservation.data.reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.data.reservation.client.PerformanceClient;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.dto.PerformanceDto;
import com.sim.reservation.data.reservation.mapper.PerformanceInfoMapper;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;

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
public class PerformanceInfoInfoSyncServiceImpl implements PerformanceInfoSyncService {
	private final PerformanceClient performanceClient;
	private final PerformanceInfoMapper performanceInfoMapper;
	private final PerformanceInfoRepository performanceInfoRepository;

	@CircuitBreaker(name = "getPerformances", fallbackMethod = "fallback")
	@Override
	public boolean requestAndSavePerformanceInfo(Long performanceId) {
		PerformanceDto performanceDto = performanceClient.getPendingPerformanceById(performanceId);
		PerformanceInfo performanceInfo = performanceInfoMapper.toEntity(performanceDto);
		performanceInfoRepository.save(performanceInfo);
		return true;
	}

	public boolean fallback(Long performanceId, Throwable ex) {
		log.error("CircuitBreaker is open. Failed to get performances by performanceId : {}", performanceId, ex);
		return false;
	}
}
