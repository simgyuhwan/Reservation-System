package com.sim.reservationservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
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
 * 공연 시간 정보
 *
 * @author sgh
 * @since 2023.04.18
 */
@Entity @Getter
@Table(name = "performance_schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformanceSchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public PerformanceSchedule(PerformanceInfo performanceInfo, LocalDate startDate, LocalDate endDate, Integer availableSeats,
        LocalTime startTime, Integer remainingSeats) {
        this.performanceInfo = performanceInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
        this.remainingSeats = remainingSeats;
    }
}
