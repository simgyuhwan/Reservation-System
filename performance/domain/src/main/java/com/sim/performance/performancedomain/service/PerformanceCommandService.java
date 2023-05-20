package com.sim.performance.performancedomain.service;

import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.type.RegisterStatusType;

public interface PerformanceCommandService {
	/**
	 * 공연 생성
	 *
	 * @param performanceCreateDto 공연 생성 DTO
	 * @return 공연등록 요청에 대한 결과값
	 */
	PerformanceStatusDto createPerformance(PerformanceCreateDto performanceCreateDto);

	/**
	 * 공연 수정
	 *
	 * @param performanceId 수정할 공연 ID
	 * @param performanceUpdateDto 수정 정보
	 * @return 수정된 공연 정보
	 */
	PerformanceDto updatePerformance(Long performanceId, PerformanceUpdateDto performanceUpdateDto);

	/**
	 * 공연 상태 수정
	 *
	 * @param performanceId 공연 ID
	 * @param registerStatusType 변경할 공연 상태 값
	 */
	void performanceChangeStatus(Long performanceId, RegisterStatusType registerStatusType);
}
