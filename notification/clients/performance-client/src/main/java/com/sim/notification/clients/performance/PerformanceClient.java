package com.sim.notification.clients.performance;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PerformanceApiClient.java
 * Performance Service에 요청 api 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
@FeignClient(name = "performance-service", path = "/api/performances")
public interface PerformanceClient {

	@GetMapping
	List<Performance> getPerformanceByMemberId(@RequestParam("memberId") Long memberId);

	@GetMapping("/{performanceId}")
	Performance getPerformanceById(@PathVariable("performanceId") Long performanceId);

	@GetMapping("/{performanceId}/status/pending")
	Performance getPendingPerformanceById(@PathVariable("performanceId") Long performanceId);
}
