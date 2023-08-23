package com.sim.reservation.boot.type;

import lombok.Getter;

@Getter
public enum ResultMessage {
    RESERVATION_APPLY_COMPLETE("예약 신청이 완료되었습니다."),
    RESERVATION_CANCEL_COMPLETE("예약 신청 취소가 완료되었습니다.");

    private final String message;

    ResultMessage(String message) {
        this.message = message;
    }
}
