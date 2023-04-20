package com.sim.reservationservice.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceSchedule.java
 * 공연 시간, 좌석 정보가 담긴 DTO
 *
 * @author sgh
 * @since 2023.04.20
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceScheduleDto {
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private Integer audienceCount;
	private Integer availableSeats;

	@QueryProjection
	public PerformanceScheduleDto(LocalDate startDate, LocalDate endDate, LocalTime startTime,
		Integer audienceCount, Integer availableSeats) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.audienceCount = audienceCount;
		this.availableSeats = availableSeats;
	}
}
