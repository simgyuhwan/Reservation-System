package com.reservation.common.error;

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
	PERFORMANCE_NOT_FOUND("Performance 조회 실패, performanceId : "),
	MEMBER_NOT_FOUND("Member 조회 실패, memberId : "),
	EVENT_NOT_FOUND("Event 조회 실패, event id : "),
	INVALID_USER_ID("잘못된 회원 ID, userId : "),
	NOT_PENDING_PERFORMANCE("pending 상태인 공연 조회 실패, performanceId : "),
	FAILURE_TO_REGISTER_PERFORMANCE_INFORMATION("예약 서비스, 공연 정보 등록 실패");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
