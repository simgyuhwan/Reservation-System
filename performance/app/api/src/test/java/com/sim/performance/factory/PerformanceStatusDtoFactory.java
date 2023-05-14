package com.sim.performance.factory;

import com.sim.performance.performancedomain.dto.PerformanceStatusDto;

public class PerformanceStatusDtoFactory {

	public static PerformanceStatusDto createPerformanceStatusDto() {
		return PerformanceStatusDto.requestComplete(1L);
	}
}
