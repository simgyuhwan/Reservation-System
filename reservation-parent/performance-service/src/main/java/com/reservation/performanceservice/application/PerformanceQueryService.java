package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceRegisterDto;

public interface PerformanceQueryService {
	void createPerformance(PerformanceRegisterDto registerDto);
}
