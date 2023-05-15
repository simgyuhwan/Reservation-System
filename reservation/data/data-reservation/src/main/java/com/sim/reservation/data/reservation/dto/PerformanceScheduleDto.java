package com.sim.reservation.data.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sim.reservation.data.reservation.domain.PerformanceSchedule;

import lombok.AccessLevel;
import lombok.Builder;
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
	private Integer remainingSeats;
	private Integer availableSeats;
	private Long performanceScheduleId;

	@Builder
	public PerformanceScheduleDto(LocalDate startDate, LocalDate endDate, LocalTime startTime,
		Integer remainingSeats, Integer availableSeats, Long performanceScheduleId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.remainingSeats = remainingSeats;
		this.availableSeats = availableSeats;
		this.performanceScheduleId = performanceScheduleId;
	}

	public static PerformanceScheduleDto from(PerformanceSchedule performanceSchedule) {
		return PerformanceScheduleDto.builder()
			.startDate(performanceSchedule.getStartDate())
			.endDate(performanceSchedule.getEndDate())
			.startTime(performanceSchedule.getStartTime())
			.remainingSeats(performanceSchedule.getRemainingSeats())
			.availableSeats(performanceSchedule.getAvailableSeats())
			.performanceScheduleId(performanceSchedule.getId())
			.build();
	}
}
