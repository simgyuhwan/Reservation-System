package com.sim.reservationservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.reservationservice.application.ReservationCommandService;
import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * ReservationController.java
 * 예약 관련 컨트롤러
 *
 * @author sgh
 * @since 2023.04.25
 */
@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationCommandService reservationCommandService;

	@Operation(summary = "[예약] 공연 예약 신청", description = "공연 예약 신청 API")
	@PostMapping("/{performanceId}/schedules/{scheduleId}/reservations")
	public ResponseEntity<ReservationInfoDto> reservation(@PathVariable Long performanceId,
		@PathVariable Long scheduleId,
		@RequestBody @Validated ReservationDto reservationDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			reservationCommandService.createReservation(performanceId, scheduleId, reservationDto));
	}

}
