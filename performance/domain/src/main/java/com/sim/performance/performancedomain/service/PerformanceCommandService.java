package com.sim.performance.performancedomain.service;

import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.type.RegisterStatusType;

public interface PerformanceCommandService {
	PerformanceStatusDto createPerformance(PerformanceCreateDto performanceCreateDto);

	PerformanceDto updatePerformance(Long performanceId, PerformanceUpdateDto performanceUpdateDto);

	void performanceChangeStatus(Long performanceId, RegisterStatusType registerStatusType);
}
