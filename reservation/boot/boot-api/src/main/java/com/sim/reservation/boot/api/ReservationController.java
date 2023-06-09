package com.sim.reservation.boot.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationCancelResponse;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;
import com.sim.reservation.boot.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
 * ReservationController.java
 * 예약 컨트롤러
 *
 * @author sgh
 * @since 2023.05.15
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	/**
	 * 공연 예약 신청
	 *
	 * @param performanceId 공연 ID
	 * @param scheduleId 공연 시간 ID
	 * @param reservationApplyRequest 공연 예약 신청 정보
	 * @return
	 */
	@PostMapping("/performances/{performanceId}/schedules/{scheduleId}/reservations")
	@Operation(summary = "[예약] 공연 예약 신청", description = "공연 예약 신청 API")
	public ResponseEntity<ReservationResultResponse> reservation(@PathVariable Long performanceId,
		@PathVariable Long scheduleId,
		@RequestBody @Validated ReservationApplyRequest reservationApplyRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			reservationService.applyReservation(performanceId, scheduleId, reservationApplyRequest));
	}

	/**
	 * 공연 예약 취소 신청
	 *
	 * @param reservationId 예약 ID
	 * @return 예약 취소 신청 응답 메시지
	 */
	@DeleteMapping("/reservations/{reservationId}")
	public ReservationCancelResponse reservationCancel(@PathVariable Long reservationId) {
		return reservationService.cancelReservation(reservationId);
	}
}
