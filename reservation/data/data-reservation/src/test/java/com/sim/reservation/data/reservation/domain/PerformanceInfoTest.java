package com.sim.reservation.data.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Gyuhwan
 */
class PerformanceInfoTest {

    @DisplayName("공연 정보에서 특정 스케줄 정보를 찾을 수 있다.")
    @Test
    void findScheduleInformation() {
        // given
        List<PerformanceSchedule> schedules = new ArrayList<>();
        PerformanceSchedule scheduleToAdd = createPerformanceSchedule(1L, 200);
        schedules.add(scheduleToAdd);

        PerformanceInfo performanceInfo = createPerformanceInfo(schedules);

        // when
        PerformanceSchedule findSchedule = performanceInfo.findScheduleById(1L).orElse(null);

        // then
        assert findSchedule != null;
        assertThat(findSchedule.getId()).isEqualTo(scheduleToAdd.getId());
        assertThat(findSchedule.getAvailableSeats()).isEqualTo(scheduleToAdd.getAvailableSeats());
    }

    @DisplayName("공연 정보에 스케줄을 추가할 수 있다.")
    @Test
    void canAddSchedules() {
        // given
        PerformanceInfo performanceInfo = createPerformanceInfo(new ArrayList<>());
        PerformanceSchedule scheduleToAdd = createPerformanceSchedule(2L, 200);
        performanceInfo.addPerformanceSchedule(scheduleToAdd);

        // when
        PerformanceSchedule findSchedule = performanceInfo.findScheduleById(2L).orElse(null);

        // then
        assert findSchedule != null;
        assertThat(findSchedule.getId()).isEqualTo(scheduleToAdd.getId());
        assertThat(findSchedule.getAvailableSeats()).isEqualTo(scheduleToAdd.getAvailableSeats());
    }

    @DisplayName("이미 등록된 스케줄이라면 중복 추가되지 않는다.")
    @Test
    void duplicateStorageIsNotPossible() {
        // given
        PerformanceInfo performanceInfo = createPerformanceInfo(new ArrayList<>());
        PerformanceSchedule scheduleToAdd = createPerformanceSchedule(1L, 100);
        performanceInfo.addPerformanceSchedule(scheduleToAdd);

        // when
        performanceInfo.addPerformanceSchedule(scheduleToAdd);
        List<PerformanceSchedule> schedules = performanceInfo.getPerformanceSchedules();

        // then
        assertThat(schedules).hasSize(1);
    }

    private static PerformanceInfo createPerformanceInfo(List<PerformanceSchedule> schedules) {
        return PerformanceInfo.builder()
            .performanceSchedules(schedules)
            .info("정보")
            .name("이름")
            .build();
    }

    private static PerformanceSchedule createPerformanceSchedule(long id, int availableSeats) {
        return PerformanceSchedule.builder()
            .id(id)
            .startTime(LocalTime.of(11, 0))
            .startDate(LocalDate.of(2023, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .availableSeats(availableSeats)
            .remainingSeats(200)
            .performanceInfo(null)
            .isAvailable(true)
            .build();
    }
}