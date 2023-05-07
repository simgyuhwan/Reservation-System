package com.reservation.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.reservation.common.dto.PerformanceDto;

/**
 * PerformanceApiClient.java
 * Performance Service에 요청 api 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
@FeignClient(name = "performance-service", path = "/api/performances")
public interface PerformanceApiClient {

	@GetMapping
	List<PerformanceDto> getPerformanceByMemberId(@RequestParam("memberId") Long memberId);

	@GetMapping("/{performanceId}")
	PerformanceDto getPerformanceById(@PathVariable("performanceId") Long performanceId);
}
