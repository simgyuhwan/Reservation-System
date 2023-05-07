package com.reservation.common.error;

/**
 * ErrorMessage.java
 * 에러 메시지
 *
 * @author sgh
 * @since 2023.04.27
 */
public enum ErrorMessage {
	PERFORMANCE_INFO_NOT_FOUND("PerformanceInfo 조회 실패, performanceInfoId : "),
	PERFORMANCE_SCHEDULE_NOT_FOUND("PerformanceSchedule 조회 실패, performanceScheduleId : "),
	RESERVATION_NOT_AVAILABLE("예약이 불가능한 공연입니다. performanceId : "),
	SOLD_OUT_PERFORMANCE("매진된 공연입니다. performanceScheduleId : "),
	PERFORMANCE_NOT_FOUND("Performance 조회 실패, performanceId : "),
	MEMBER_NOT_FOUND("Member 조회 실패, memberId : "),
	EVENT_NOT_FOUND("Event 조회 실패, event id : "),
	INVALID_USER_ID("잘못된 회원 ID, userId : "),
	FAILED_TO_SEARCH_PERFORMANCE_IN_RESERVATION_SERVICE("");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
