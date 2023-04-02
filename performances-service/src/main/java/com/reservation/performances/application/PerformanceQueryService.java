package com.reservation.performances.application;

import com.reservation.performances.dto.request.PerformanceRegisterDto;

public interface PerformanceQueryService {
	void registerPerformance(PerformanceRegisterDto registerDto);
}
