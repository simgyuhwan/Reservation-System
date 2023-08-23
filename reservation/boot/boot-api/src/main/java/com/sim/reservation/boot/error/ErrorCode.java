package com.sim.reservation.boot.error;

import lombok.Getter;

/**
 * ErrorCode.java
 * HTTP 에러 반환 메시지 및 코드
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public enum ErrorCode {
	RESERVATION_SEARCH_VALUE_INVALID("예약 가능한 공연 조회 값이 올바르지 않습니다.", 400),
	INVALID_PERFORMANCE_RESERVATION_INFORMATION("잘못된 공연 예약 정보입니다.", 400),
	PERFORMANCE_SOLD_OUT_ERROR_MESSAGE("죄송합니다. 해당 공연은 매진되어 예약이 불가능합니다.", 400),
	NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE("죄송합니다. 해당 공연은 등록되어 있지 않습니다.", 404),
	RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE("죄송합니다. 해당 공연은 예약이 불가능합니다.", 400),
	SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE("공연 일정을 다시 확인해주세요.", 400),
	RESERVATION_FAILED_DUE_TO_SERVER_FAILURE("서버 상의 장애로 예약이 실패했습니다.", 500),
	RESERVATION_INFO_NOT_FOUND_MESSAGE("잘못된 예약 정보입니다.", 400),
	INVALID_DATE_FORMAT("잘못된 날짜 형식입니다. ex) 날짜는 2023-06-18, 시간은 12:30", 400),
	RESERVATION_INFORMATION_NOT_FOUND("예약 정보를 찾을 수 없습니다.", 400)
	;

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
