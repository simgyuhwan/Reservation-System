package com.sim.reservationservice.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.domain.Reservation;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "예약 ID", example = "1")
	private Long id;
	@Schema(description = "공연 이름", example = "오페라의 유령")
	private String performanceName;
	@Schema(description = "공연 날짜", example = "2023-06-01")
	private LocalDate date;
	@Schema(description = "공연 시간", example = "13:00:00")
	private LocalTime time;
	@Schema(description = "이용자 이름", example = "홍길동")
	private String name;
	@Schema(description = "이용자 핸드폰 번호", example = "010-1111-2222")
	private String phoneNum;
	@Schema(description = "이용자 이메일", example = "test@naver.com")
	private String email;
	@Schema(description = "예약 상태", example = "예약 완료")
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
