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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.performanceservice.application.PerformanceQueryService;
import com.reservation.performanceservice.application.PerformanceCommandService;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.CreatedResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class PerformanceController {
	private final PerformanceCommandService performanceCommandService;
	private final PerformanceQueryService performanceQueryService;

	@PostMapping
	@Operation(summary = "[공연] 공연 등록 요청", description = "공연 등록 API")
	public ResponseEntity<CreatedResponseDto> performanceRegister(@RequestBody @Validated PerformanceDto registerDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(performanceCommandService.createPerformance(registerDto));
	}

	@PutMapping("/{performanceId}")
	@Operation(summary = "[공연] 공연 수정", description = "공연 수정 API")
	public ResponseEntity<PerformanceDto> performanceUpdate(@RequestBody @Validated PerformanceDto updateDto, @PathVariable Long performanceId) {
		return ResponseEntity.ok(performanceCommandService.updatePerformance(performanceId, updateDto));
	}

	@GetMapping
	@Operation(summary = "[공연] 회원 ID 전체 공연 조회", description = "회원 공연 조회 API")
	public List<PerformanceDto> performanceSelectAll(@RequestParam Long memberId) {
		return performanceQueryService.selectPerformances(memberId);
	}

	@GetMapping("/{performanceId}")
	@Operation(summary = "[공연] 공연 상세 조회", description = "공연 ID로 공연 상세 조회" )
	public PerformanceDto performanceSelectById(@PathVariable Long performanceId) {
		return performanceQueryService.selectPerformanceById(performanceId);
	}
}
