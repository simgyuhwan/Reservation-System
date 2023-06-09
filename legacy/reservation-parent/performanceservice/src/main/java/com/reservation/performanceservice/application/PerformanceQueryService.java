package com.reservation.performanceservice.application;

import java.util.List;

import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.PerformanceStatusDto;

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
