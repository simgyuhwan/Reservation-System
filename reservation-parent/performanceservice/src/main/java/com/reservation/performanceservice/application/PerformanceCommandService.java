package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.CreatedResponseDto;

public interface PerformanceCommandService {
	CreatedResponseDto createPerformance(PerformanceDto registerDto);

	PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto);
}
