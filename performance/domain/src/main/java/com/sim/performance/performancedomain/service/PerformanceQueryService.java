package com.sim.performance.performancedomain.service;

import java.util.List;

import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;

/**
 * PerformanceCommandService.java
 * 공연 Command 관련 서비스
 *
 * @author sgh
 * @since 2023.04.06
 */
public interface PerformanceQueryService {

	/**
	 * 회원 ID로 공연 정보 조회
	 *
	 * @param memberId 회원 식별자 ID
	 * @return 회원이 등록한 공연 정보
	 */
	List<PerformanceDto> selectPerformances(Long memberId);

	/**
	 * 공연 정보 조회
	 *
	 * @param performanceId 조회할 공연 ID
	 * @return 공연 정보
	 */
	PerformanceDto selectPerformanceById(Long performanceId);

	/**
	 * 공연 등록 신청된 공연 정보 조회
	 *
	 * @param performanceId 조회할 공연 ID
	 * @return 공연 정보
	 */
	PerformanceDto selectPendingPerformanceById(Long performanceId);

	/**
	 * 공연 등록 신청 상태값 조회
	 *
	 * @param performanceId 조회할 공연 ID
	 * @return 공연 등록 상태 정보
	 */
	PerformanceStatusDto getPerformanceStatusByPerformanceId(Long performanceId);
}
