package com.reservation.performanceservice.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.performanceservice.application.PerformanceCommandService;
import com.reservation.performanceservice.application.PerformanceQueryService;
import com.reservation.performanceservice.dto.request.PerformanceDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceQueryService performanceQueryService;
	private final PerformanceCommandService performanceCommandService;

	@PostMapping
	@Operation(summary = "[공연] 공연 등록", description = "공연 등록 API")
	public ResponseEntity performanceRegister(@RequestBody @Validated PerformanceDto registerDto) {
		performanceQueryService.createPerformance(registerDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{performanceId}")
	@Operation(summary = "[공연] 공연 수정", description = "공연 수정 API")
	public ResponseEntity performanceUpdate(@RequestBody @Validated PerformanceDto updateDto, @PathVariable Long performanceId) {
		return ResponseEntity.ok(performanceQueryService.updatePerformance(performanceId, updateDto));
	}

	@GetMapping("/{userId}")
	public List<PerformanceDto> performanceSelectAll(@PathVariable String userId) {
		return performanceCommandService.selectPerformances(userId);
	}
}
