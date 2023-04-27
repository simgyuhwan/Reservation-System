package com.sim.reservationservice.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.domain.Reservation;

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
	private String email;
	private String status;

	@Builder
	public ReservationInfoDto(Long id, String performanceName, LocalDate date, LocalTime time, String name,
		String phoneNum, String email, String status) {
		this.id = id;
		this.performanceName = performanceName;
		this.date = date;
		this.time = time;
		this.name = name;
		this.phoneNum = phoneNum;
		this.status = status;
		this.email = email;
	}

	public static ReservationInfoDto of(Reservation reservation, PerformanceSchedule schedule, String performanceName) {
		return ReservationInfoDto.builder()
			.id(reservation.getId())
			.performanceName(performanceName)
			.date(schedule.getStartDate())
			.time(schedule.getStartTime())
			.name(reservation.getName())
			.phoneNum(reservation.getPhoneNum())
			.email(reservation.getEmail())
			.status(reservation.getStatusToString())
			.build();
	}
}
