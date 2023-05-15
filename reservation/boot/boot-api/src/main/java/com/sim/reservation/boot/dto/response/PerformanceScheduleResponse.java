package com.sim.reservation.boot.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceScheduleResponse.java
 * 공연 스케줄 Response
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceScheduleResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private Integer remainingSeats;
    private Integer availableSeats;
    private Long performanceScheduleId;

    @Builder
    public PerformanceScheduleResponse(LocalDate startDate, LocalDate endDate, LocalTime startTime,
        Integer remainingSeats, Integer availableSeats, Long performanceScheduleId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.remainingSeats = remainingSeats;
        this.availableSeats = availableSeats;
        this.performanceScheduleId = performanceScheduleId;
    }

}
