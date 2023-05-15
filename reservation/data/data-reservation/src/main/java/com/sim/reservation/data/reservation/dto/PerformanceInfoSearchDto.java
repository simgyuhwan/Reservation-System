package com.sim.reservation.data.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfoSearchDto.java
 * 공연 조회용 DTO
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor
public class PerformanceInfoSearchDto {
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String name;
	private String type;
	private String place;

	@Builder
	public PerformanceInfoSearchDto(LocalDate startDate, LocalDate endDate, LocalTime startTime,
		LocalTime endTime, String name, String type, String place) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.type = type;
		this.place = place;
	}
}
