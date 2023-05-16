package com.sim.performance.api;

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

import com.sim.performance.dto.request.PerformanceCreateRequest;
import com.sim.performance.dto.request.PerformanceUpdateRequest;
import com.sim.performance.dto.response.PerformanceInfoResponse;
import com.sim.performance.dto.response.PerformanceUpdateResponse;
import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.service.PerformanceCommandService;
import com.sim.performance.performancedomain.service.PerformanceQueryService;

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
	public ResponseEntity<PerformanceStatusDto> performanceRegister(@RequestBody @Validated PerformanceCreateRequest performanceCreateRequest) {
		PerformanceCreateDto performanceCreateDto = performanceCreateRequest.toDto();
		return ResponseEntity.status(HttpStatus.CREATED).body(performanceCommandService.createPerformance(performanceCreateDto));
	}

	@PutMapping("/{performanceId}")
	@Operation(summary = "[공연] 공연 수정", description = "공연 수정 API")
	public PerformanceUpdateResponse performanceUpdate(@RequestBody @Validated PerformanceUpdateRequest updateDto, @PathVariable Long performanceId) {
		PerformanceDto performanceDto = performanceCommandService.updatePerformance(performanceId, updateDto.toDto());
		return PerformanceUpdateResponse.from(performanceDto);
	}

	@GetMapping
	@Operation(summary = "[공연] 회원이 등록한 전체 공연 조회", description = "회원 등록 공연 조회 API")
	public List<PerformanceInfoResponse> performanceSelectAll(@RequestParam Long memberId) {
		List<PerformanceDto> performanceDtos = performanceQueryService.selectPerformances(memberId);
		return PerformanceInfoResponse.from(performanceDtos);
	}

	@GetMapping("/{performanceId}")
	@Operation(summary = "[공연] 등록된 공연 상세 조회", description = "공연 ID로 등록된 공연 상세 조회 API" )
	public PerformanceInfoResponse performanceSelectById(@PathVariable Long performanceId) {
		PerformanceDto performanceDto = performanceQueryService.selectPerformanceById(performanceId);
		return PerformanceInfoResponse.from(performanceDto);
	}

	@GetMapping("/{performanceId}/status/pending")
	@Operation(summary = "[공연] 등록 신청 중인 공연 정보 조회", description = "등록 신청 중인 공연 정보 조회 API")
	public PerformanceInfoResponse unregisteredPerformanceInfo(@PathVariable Long performanceId) {
		PerformanceDto performanceDto = performanceQueryService.selectPendingPerformanceById(performanceId);
		return PerformanceInfoResponse.from(performanceDto);
	}

	@GetMapping("/{performanceId}/status")
	@Operation(summary = "[공연] 공연 등록 상태 조회", description = "공연 등룩 후 등록 상태 확인")
	public PerformanceStatusDto performanceStatus(@PathVariable Long performanceId) {
		return performanceQueryService.getPerformanceStatusByPerformanceId(performanceId);
	}
}
