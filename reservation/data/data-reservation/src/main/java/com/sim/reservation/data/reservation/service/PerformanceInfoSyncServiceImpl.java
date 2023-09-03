package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.client.PerformanceClient;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.dto.PerformanceDto;
import com.sim.reservation.data.reservation.mapper.PerformanceInfoMapper;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;
import com.sim.reservation.data.reservation.repository.PerformanceScheduleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 정보를 예약 서비스에 동기화하는 서비스
 *
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceInfoSyncServiceImpl implements PerformanceInfoSyncService {
	private final PerformanceClient performanceClient;
	private final PerformanceInfoMapper performanceInfoMapper;
	private final PerformanceInfoRepository performanceInfoRepository;

	/**
	 * 공연 서비스에 공연 정보 조회 후 저장
	 */
	@CircuitBreaker(name = "getNewPerformance", fallbackMethod = "fallback")
	@Override
	public boolean requestAndSavePerformanceInfo(Long performanceId) {
		PerformanceDto performanceDto = performanceClient.getPendingPerformanceById(performanceId);
		PerformanceInfo performanceInfo = performanceInfoMapper.toEntity(performanceDto);
		performanceInfoRepository.save(performanceInfo);
		return true;
	}

	/**
	 * 공연 서비스에 공연 정보 조회 후 수정
	 */
	@CircuitBreaker(name = "getUpdatePerformance", fallbackMethod = "fallback")
	@Override
	public boolean requestAndUpdatePerformanceInfo(Long performanceId) {
		PerformanceDto performanceDto = performanceClient.getPerformanceById(performanceId);
		PerformanceInfo performanceInfo = performanceInfoRepository.findByPerformanceId(performanceId)
			.orElseThrow(EntityNotFoundException::new);

		updatePerformanceInfo(performanceDto, performanceInfo);
		return true;
	}

	public boolean fallback(Long performanceId, Throwable ex) {
		log.error("CircuitBreaker is open. Failed to get performances by performanceId : {}", performanceId, ex);
		return false;
	}

	private void updatePerformanceInfo(PerformanceDto performanceDto, PerformanceInfo performanceInfo) {
		performanceInfo.setName(performanceDto.getPerformanceName());
		performanceInfo.setInfo(performanceDto.getPerformanceInfo());
		performanceInfo.setPlace(performanceDto.getPerformancePlace());
		performanceInfo.setPrice(performanceDto.getPrice());
		performanceInfo.setContactPersonName(performanceDto.getContactPersonName());
		performanceInfo.setContactPhoneNum(performanceDto.getContactPhoneNum());
	}

}
