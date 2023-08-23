package com.sim.reservation.data.reservation.error;

import lombok.Getter;

@Getter
public class ReservationInfoNotFoundException extends RuntimeException {

    private final Long id;

    public ReservationInfoNotFoundException(ErrorMessage message, Long id) {
        super(message.getMessage() + id);
        this.id = id;
    }
}
