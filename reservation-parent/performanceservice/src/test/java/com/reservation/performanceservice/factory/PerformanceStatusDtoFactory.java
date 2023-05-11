package com.reservation.performanceservice.factory;

import com.reservation.performanceservice.dto.response.PerformanceStatusDto;

public class PerformanceStatusDtoFactory {

	public static PerformanceStatusDto createPerformanceStatusDto() {
		return PerformanceStatusDto.requestComplete(1L);
	}
}
