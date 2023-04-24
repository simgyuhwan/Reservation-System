package com.sim.reservationservice.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.reservationservice.application.PerformanceQueryService;
import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationController.java
 * 예약 컨트롤러
 *
 * @author sgh
 * @since 2023.04.11
 */
@Slf4j
@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class ReservationController {
	private final PerformanceQueryService performanceQueryService;
	private final RedisTemplate redisTemplate;

	@GetMapping("/available")
	@Operation(summary = "[예약] 예약 현황 조회", description = "예약 현황 조회 API")
	public ResponseEntity<Page<PerformanceInfoDto>> getPerformances(@ModelAttribute @Validated PerformanceSearchDto performanceSearchDto, Pageable pageable) {
		return ResponseEntity.ok(performanceQueryService.selectPerformances(performanceSearchDto, pageable));
	}

}
