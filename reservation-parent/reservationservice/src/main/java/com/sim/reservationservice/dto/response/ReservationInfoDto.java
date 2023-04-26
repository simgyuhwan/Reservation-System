package com.sim.reservationservice.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReservationInfoDto.java
 * 공연 예약 응답 값
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationInfoDto {
	private Long id;
	private String performanceName;
	private LocalDate date;
	private LocalTime time;
	private String name;
	private String phoneNum;

	@Builder
	public ReservationInfoDto(Long id, String performanceName, LocalDate date, LocalTime time, String name,
		String phoneNum) {
		this.id = id;
		this.performanceName = performanceName;
		this.date = date;
		this.time = time;
		this.name = name;
		this.phoneNum = phoneNum;
	}
}
