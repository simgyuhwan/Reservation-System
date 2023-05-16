package com.sim.reservation.boot.factory;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;

/**
 * ReservationApplyRequestFactory.java
 * 공연 예약 요청 Factory 클래스
 *
 * @author sgh
 * @since 2023.05.16
 */
public class ReservationApplyRequestFactory {
    public static ReservationApplyRequest createReservationApplyRequest(String userId, String name, String phoneNum, String email, boolean isEmailReceiveDenied, boolean isSnsReceiveDenied) {
        return ReservationApplyRequest.builder()
            .userId(userId)
            .name(name)
            .email(email)
            .phoneNum(phoneNum)
            .isEmailReceiveDenied(isEmailReceiveDenied)
            .isSnsReceiveDenied(isSnsReceiveDenied)
            .build();
    }
}
