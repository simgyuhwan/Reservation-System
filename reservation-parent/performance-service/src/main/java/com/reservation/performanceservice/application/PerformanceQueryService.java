package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceRegistrationDto;

public interface PerformanceQueryService {
	void createPerformance(PerformanceRegistrationDto registerDto);
}
