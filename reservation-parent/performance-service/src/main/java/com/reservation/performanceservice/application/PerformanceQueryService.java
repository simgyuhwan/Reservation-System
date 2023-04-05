package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceDto;

public interface PerformanceQueryService {
	void createPerformance(PerformanceDto registerDto);
}
