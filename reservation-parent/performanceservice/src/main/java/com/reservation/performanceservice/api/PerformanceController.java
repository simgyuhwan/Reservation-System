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

import com.reservation.performanceservice.application.PerformanceCommandService;
import com.reservation.performanceservice.application.PerformanceQueryService;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.PerformanceStatusDto;

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
	public ResponseEntity<PerformanceStatusDto> performanceRegister(@RequestBody @Validated PerformanceDto registerDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(performanceCommandService.createPerformance(registerDto));
	}

	@PutMapping("/{performanceId}")
	@Operation(summary = "[공연] 공연 수정", description = "공연 수정 API")
	public PerformanceDto performanceUpdate(@RequestBody @Validated PerformanceDto updateDto, @PathVariable Long performanceId) {
		return performanceCommandService.updatePerformance(performanceId, updateDto);
	}

	@GetMapping
	@Operation(summary = "[공연] 회원이 등록한 전체 공연 조회", description = "회원 등록 공연 조회 API")
	public List<PerformanceDto> performanceSelectAll(@RequestParam Long memberId) {
		return performanceQueryService.selectPerformances(memberId);
	}

	@GetMapping("/{performanceId}")
	@Operation(summary = "[공연] 등록된 공연 상세 조회", description = "공연 ID로 등록된 공연 상세 조회 API" )
	public PerformanceDto performanceSelectById(@PathVariable Long performanceId) {
		return performanceQueryService.selectPerformanceById(performanceId);
	}

	@GetMapping("/{performanceId}/status/pending")
	@Operation(summary = "[공연] 등록 신청 중인 공연 정보 조회", description = "등록 신청 중인 공연 정보 조회 API")
	public PerformanceDto unregisteredPerformanceInfo(@PathVariable Long performanceId) {
		return performanceQueryService.selectPendingPerformanceById(performanceId);
	}

	@GetMapping("/{performanceId}/status")
	public PerformanceStatusDto performanceStatus(@PathVariable Long performanceId) {
		return performanceQueryService.getPerformanceStatusByPerformanceId(performanceId);
	}
}
