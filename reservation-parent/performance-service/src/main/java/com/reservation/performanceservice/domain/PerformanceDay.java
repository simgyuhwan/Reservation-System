package com.reservation.performanceservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reservation.common.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceDate.java
 * 공연 날짜 Entity
 *
 * @author sgh
 * @since 2023.04.03
 */
@Getter @Entity
@Table(name = "performance_day")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformanceDay extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	private LocalTime time;

	private LocalDate start;

	private LocalDate end;

	@Builder
	public PerformanceDay(Performance performance, LocalTime time, LocalDate start, LocalDate end) {
		this.performance = performance;
		this.time = time;
		this.start = start;
		this.end = end;
	}
}
