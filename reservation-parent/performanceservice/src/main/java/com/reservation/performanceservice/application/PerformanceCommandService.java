package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceDto;

public interface PerformanceCommandService {
	void createPerformance(PerformanceDto registerDto);

	PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto);
}
