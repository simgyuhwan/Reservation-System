package com.reservation.performanceservice.application;

import java.util.List;

import com.reservation.performanceservice.dto.request.PerformanceDto;

/**
 * PerformanceCommandService.java
 * 공연 Command 관련 서비스
 *
 * @author sgh
 * @since 2023.04.06
 */
public interface PerformanceCommandService {
	List<PerformanceDto> selectPerformances(String userId);
}
