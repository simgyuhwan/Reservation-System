package com.sim.reservation.data.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sim.reservation.data.reservation.type.ReservationStatusType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by Gyuhwan
 */
class ReservationTest {

    private final static String USER_ID = "test";

    private final static String NAME = "홍길동";

    private final static String PHONE_NUM = "010-1234-1234";

    private final static String EMAIL = "test@google.com";

    private final static boolean EMAIL_RECEIVE_DENIED = true;
    private final static boolean SMS_RECEIVE_DENIED = true;

    @DisplayName("예약 정보를 생성할 수 있다.")
    @Test
    void reservationInformationCanBeCreated() {
        Reservation reservation = createReservation();

        assertThat(reservation.getUserId()).isEqualTo(USER_ID);
        assertThat(reservation.getName()).isEqualTo(NAME);
        assertThat(reservation.getPhoneNum()).isEqualTo(PHONE_NUM);
    }

    @DisplayName("예약을 취소할 수 있다.")
    @Test
    void canCancelYourReservation() {
        // given
        Reservation reservation = createReservation();

        // when
        reservation.cancelReservation();

        // then
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatusType.CANCELLATION_REQUESTED);
    }

    @DisplayName("예약 상태를 문자열로 받을 수 있다.")
    @Test
    void returnReservationStatusString() {
        // given
        Reservation reservation = createReservation();
        String expectedStatus = ReservationStatusType.PAYMENT_PENDING.getStatus();

        // when
        String result = reservation.getStatusToString();

        // then
        assertThat(result).isEqualTo(expectedStatus);
    }

    @DisplayName("결제 완료시 결제 완료로 상태가 변경된다.")
    @Test
    void changePaymentCompletionStatus() {
        // given
        Reservation reservation = createReservation();

        // when
        reservation.completePayment();

        // then
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatusType.PAYMENT_COMPLETED);
    }

    private static Reservation createReservation() {
        Reservation reservation = Reservation.builder()
            .userId(ReservationTest.USER_ID)
            .name(ReservationTest.NAME)
            .phoneNum(PHONE_NUM)
            .email(EMAIL)
            .isEmailReceiveDenied(EMAIL_RECEIVE_DENIED)
            .isSmsReceiveDenied(SMS_RECEIVE_DENIED)
            .status(ReservationStatusType.PAYMENT_PENDING)
            .build();
        return reservation;
    }
}