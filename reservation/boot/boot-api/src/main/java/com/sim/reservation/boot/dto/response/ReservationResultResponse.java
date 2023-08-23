package com.sim.reservation.boot.dto.response;

import com.sim.reservation.boot.type.ResultMessage;
import com.sim.reservation.data.reservation.dto.ReservationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReservationResultResponse.java
 * 예약 신청 결과 Response
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationResultResponse {
	private Long reservationId;
	private String message;

	@Builder
	public ReservationResultResponse(Long reservationId, ResultMessage message) {
		this.reservationId = reservationId;
		this.message = message.getMessage();
	}

	public static ReservationResultResponse applyComplete(ReservationDto reservationDto) {
		return ReservationResultResponse.builder()
			.reservationId(reservationDto.getReservationId())
			.message(ResultMessage.RESERVATION_APPLY_COMPLETE)
			.build();
	}

	public static ReservationResultResponse applyComplete(Long reservationId) {
		return ReservationResultResponse.builder()
			.reservationId(reservationId)
			.message(ResultMessage.RESERVATION_APPLY_COMPLETE)
			.build();
	}

}
