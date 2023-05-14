package com.reservation.performanceservice.application;

import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.PerformanceStatusDto;
import com.reservation.performanceservice.types.RegisterStatusType;

public interface PerformanceCommandService {
	PerformanceStatusDto createPerformance(PerformanceDto registerDto);

	PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto);

	void performanceChangeStatus(Long performanceId, RegisterStatusType registerStatusType);
}
