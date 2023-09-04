package com.sim.reservation.data.reservation.domain;

import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.SoldOutException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PerformanceDate.java
 * 공연 시간 정보
 *
 * @author sgh
 * @since 2023.04.18
 */
@Entity
@Getter
@Setter
@Table(name = "performance_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSchedule extends BaseEntity {
	private static final int NO_SEAT = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "performance_schedule_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_info_id")
	private PerformanceInfo performanceInfo;

	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private Integer availableSeats;
	private Integer remainingSeats;
	private boolean isAvailable;

	@Builder
	public PerformanceSchedule(Long id, PerformanceInfo performanceInfo, LocalDate startDate, LocalDate endDate,
		Integer availableSeats,
		LocalTime startTime, Integer remainingSeats, boolean isAvailable) {
		this.id = id;
		this.performanceInfo = performanceInfo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.availableSeats = availableSeats;
		this.remainingSeats = remainingSeats;
		this.isAvailable = isAvailable;
	}

	public void reserveSeat() {
		if (isSoldOut()) {
			throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, id);
		}
		decreaseRemainingSeats();
		updateAvailability();
	}

	public void setPerformanceInfo(PerformanceInfo performanceInfo) {
		this.performanceInfo = performanceInfo;
	}

	private void updateAvailability() {
		if (remainingSeats == NO_SEAT) {
			isAvailable = false;
		}
	}

	private boolean isSoldOut() {
		return remainingSeats <= 0 || !isAvailable;
	}

	private void decreaseRemainingSeats() {
		--remainingSeats;
	}

}
