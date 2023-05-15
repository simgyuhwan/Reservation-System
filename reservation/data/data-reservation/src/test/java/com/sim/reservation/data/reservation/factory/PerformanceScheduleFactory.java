package com.sim.reservation.data.reservation.factory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * PerformanceScheduleFactory.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.15
 */
public class PerformanceScheduleFactory {
    public static final LocalDate START_DATE = LocalDate.now();
    public static final LocalDate END_DATE = LocalDate.now().plusYears(1);
    public static final LocalTime START_TIME = LocalTime.of(12, 00);
    public static final int AVAILABLE_SEATS = 100;
    public static final int REMAINING_SEATS = 100;
    public static final boolean IS_AVAILABLE = true;
    public static final long PERFORMANCE_SCHEDULE_ID = 1L;

    public static PerformanceSchedule createPerformanceSchedule(PerformanceInfo performanceInfo) {
        return PerformanceSchedule.builder()
            .id(PERFORMANCE_SCHEDULE_ID)
            .performanceInfo(performanceInfo)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .startTime(START_TIME)
            .availableSeats(AVAILABLE_SEATS)
            .remainingSeats(REMAINING_SEATS)
            .isAvailable(IS_AVAILABLE)
            .build();
    }

    public static PerformanceSchedule createDisablePerformanceSchedule(PerformanceInfo performanceInfo) {
        return PerformanceSchedule.builder()
            .id(PERFORMANCE_SCHEDULE_ID)
            .performanceInfo(performanceInfo)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .startTime(START_TIME)
            .availableSeats(AVAILABLE_SEATS)
            .remainingSeats(REMAINING_SEATS)
            .isAvailable(false)
            .build();
    }


    public static PerformanceSchedule createSoldOutPerformanceSchedule(PerformanceInfo performanceInfo) {
        return PerformanceSchedule.builder()
            .id(PERFORMANCE_SCHEDULE_ID)
            .performanceInfo(performanceInfo)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .startTime(START_TIME)
            .availableSeats(AVAILABLE_SEATS)
            .remainingSeats(0)
            .isAvailable(false)
            .build();
    }

    public static List<PerformanceSchedule> createPerformanceSchedules(PerformanceInfo performanceInfo) {
        PerformanceSchedule performanceSchedule = createPerformanceSchedule(performanceInfo);
        return List.of(performanceSchedule);
    }

    public static List<PerformanceSchedule> createPerformanceSchedulesOneSeats(PerformanceInfo performanceInfo) {
        PerformanceSchedule performanceSchedule = PerformanceSchedule.builder()
            .id(PERFORMANCE_SCHEDULE_ID)
            .performanceInfo(performanceInfo)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .startTime(START_TIME)
            .availableSeats(AVAILABLE_SEATS)
            .remainingSeats(1)
            .isAvailable(IS_AVAILABLE)
            .build();
        return List.of(performanceSchedule);
    }
}
