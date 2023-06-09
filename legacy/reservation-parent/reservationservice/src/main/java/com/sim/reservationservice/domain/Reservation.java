package com.sim.reservationservice.domain;

import com.reservation.common.model.BaseEntity;
import com.sim.reservationservice.dto.request.ReservationDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Reservation.java
 * 예약 도메인 클래스
 *
 * @author sgh
 * @since 2023.04.27
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reservation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;

	private String name;

	private String phoneNum;

	private String email;

	private boolean isEmailReceiveDenied;
	private boolean isSnsReceiveDenied;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_schedule_id")
	private PerformanceSchedule performanceSchedule;

	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	public String getStatusToString() {
		return status.getStatus();
	}

	public static Reservation of(ReservationDto reservationDto, PerformanceSchedule schedule) {
		return Reservation.builder()
			.userId(reservationDto.getUserId())
			.name(reservationDto.getName())
			.email(reservationDto.getEmail())
			.phoneNum(reservationDto.getPhoneNum())
			.isEmailReceiveDenied(reservationDto.isEmailReceiveDenied())
			.isSnsReceiveDenied(reservationDto.isSnsReceiveDenied())
			.performanceSchedule(schedule)
			.status(ReservationStatus.RESERVED)
			.build();
	}

	@Builder
	private Reservation(String userId, String name, String phoneNum, String email, boolean isEmailReceiveDenied,
		boolean isSnsReceiveDenied, PerformanceSchedule performanceSchedule,
		ReservationStatus status) {
		this.userId = userId;
		this.name = name;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isEmailReceiveDenied = isEmailReceiveDenied;
		this.isSnsReceiveDenied = isSnsReceiveDenied;
		this.performanceSchedule = performanceSchedule;
		this.status = status;
	}
}
