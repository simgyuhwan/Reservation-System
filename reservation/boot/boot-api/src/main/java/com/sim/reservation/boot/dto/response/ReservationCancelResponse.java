package com.sim.reservation.boot.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 취소 신청 응답 메시지
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelResponse {
	private String message;

	private ReservationCancelResponse(String message) {
		this.message = message;
	}

	public static ReservationCancelResponse ofSuccess() {
		return new ReservationCancelResponse("예약 취소가 완료되었습니다.");
	}
}
