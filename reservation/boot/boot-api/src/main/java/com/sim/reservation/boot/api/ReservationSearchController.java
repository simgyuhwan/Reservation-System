package com.sim.reservation.boot.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.dto.response.ReservationInfoResponse;
import com.sim.reservation.boot.service.ReservationSearchService;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.ReservationInfo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationController.java
 * 공연 정보 컨트롤러
 *
 * @author sgh
 * @since 2023.04.11
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationSearchController {
	private final ReservationSearchService reservationSearchService;

	@GetMapping("/performances/available")
	@Operation(summary = "[예약] 공연 예약 현황 조회", description = "공연 예약 현황 조회 API")
	public ResponseEntity<Page<PerformanceInfoDto>> getPerformances(@ModelAttribute @Validated PerformanceSearchRequest performanceSearchRequest, Pageable pageable) {
		return ResponseEntity.ok(reservationSearchService.getAvailablePerformances(performanceSearchRequest, pageable));
	}

	@GetMapping("/reservations/{reservationId}")
	@Operation(summary = "[예약] 공연 예약 정보 조회", description = "예약된 정보 조회 API" )
	public ReservationInfoResponse getReservationInfo(@PathVariable Long reservationId) {
		return reservationSearchService.getReservationInfo(reservationId);
	}
}
