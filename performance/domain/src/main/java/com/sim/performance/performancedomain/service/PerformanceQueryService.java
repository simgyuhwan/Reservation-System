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
	List<PerformanceDto> selectPerformances(Long memberId);

	PerformanceDto selectPerformanceById(Long performanceId);

	PerformanceDto selectPendingPerformanceById(Long performanceId);

	PerformanceStatusDto getPerformanceStatusByPerformanceId(Long performanceId);
}
