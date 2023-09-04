package com.sim.reservation.data.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sim.reservation.data.reservation.error.SoldOutException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Gyuhwan
 */
class PerformanceScheduleTest {

    @DisplayName("남은 좌석이 있으면 스케줄에 예약할 수 있다.")
    @Test
    void canMakeAReservation() {
        // given
        PerformanceSchedule schedule = createPerformanceSchedule(200, true);

        // when
        schedule.reserveSeat();

        // then
        assertThat(schedule.getRemainingSeats()).isEqualTo(199);
    }

    @DisplayName("예약 후 남은 예약 좌석이 없다면 매진 예외가 발생한다.")
    @Test
    void ifThereAreNoSeatsReservationsAreNotPossible() {
        PerformanceSchedule schedule = createPerformanceSchedule(1, true);
        schedule.reserveSeat();

        assertThatThrownBy(schedule::reserveSeat)
            .isInstanceOf(SoldOutException.class);
    }

    @DisplayName("예약 후 남은 좌석이 없다면 예약할 수 없다.")
    @Test
    void ifThereAreNoSeatsLeftTheyAreSoldOut() {
        // given
        PerformanceSchedule schedule = createPerformanceSchedule(1, true);

        // when
        schedule.reserveSeat();

        // then
        assertThat(schedule.isAvailable()).isFalse();
    }

    @DisplayName("남은 좌석이 없다면 예약은 불가능하다.")
    @Test
    void soldOutExceptionOccurs() {
        PerformanceSchedule schedule = createPerformanceSchedule(0, true);

        assertThatThrownBy(schedule::reserveSeat)
            .isInstanceOf(SoldOutException.class);
    }

    @DisplayName("남은 좌석이 있지만 예약 불가 상태라면 예약할 수없다.")
    @Test
    void reservationUnavailable() {
        PerformanceSchedule schedule = createPerformanceSchedule(200, false);

        assertThatThrownBy(schedule::reserveSeat)
            .isInstanceOf(SoldOutException.class);
    }
    private static PerformanceSchedule createPerformanceSchedule(int remainingSeats,
        boolean isAvailable) {
        return PerformanceSchedule.builder()
            .startTime(LocalTime.of(11, 0))
            .startDate(LocalDate.of(2023, 1, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .availableSeats(200)
            .remainingSeats(remainingSeats)
            .performanceInfo(null)
            .isAvailable(isAvailable)
            .build();
    }

}