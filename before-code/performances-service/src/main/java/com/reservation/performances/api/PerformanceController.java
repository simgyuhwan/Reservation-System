package com.reservation.performances.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.performances.application.PerformanceQueryService;
import com.reservation.performances.dto.request.PerformanceRegisterDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceQueryService performanceQueryService;

	@PostMapping
	@Operation(summary = "[공연] 공연 등록", description = "공연 등록 API")
	public ResponseEntity performanceRegister(@RequestBody @Validated PerformanceRegisterDto registerDto) {
		performanceQueryService.createPerformance(registerDto);
		return ResponseEntity.ok().build();
	}
}
