package com.sim.reservation.data.reservation.error;

import lombok.Getter;

/**
 * ErrorMessage.java
 * 에러 메시지
 *
 * @author sgh
 * @since 2023.04.27
 */
@Getter
public enum ErrorMessage {
	PERFORMANCE_INFO_NOT_FOUND("PerformanceInfo 조회 실패, performanceInfoId : "),
	PERFORMANCE_SCHEDULE_NOT_FOUND("PerformanceSchedule 조회 실패, performanceScheduleId : "),
	RESERVATION_NOT_AVAILABLE("예약이 불가능한 공연입니다. performanceId : "),
	SOLD_OUT_PERFORMANCE("매진된 공연입니다. performanceScheduleId : "),
	EVENT_NOT_FOUND("Event 조회 실패, event id : "),
	FAILURE_TO_REGISTER_PERFORMANCE_INFORMATION("예약 서비스, 공연 정보 등록 실패"),
	NO_MATCHING_PERFORMANCE_TIMES("일치하는 공연 시간이 없습니다. performanceScheduleId : "),
	FAILURE_TO_UPDATE_PERFORMANCE_INFORMATION("예약 서비스, 공연 정보 수정 실패")
	;

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
