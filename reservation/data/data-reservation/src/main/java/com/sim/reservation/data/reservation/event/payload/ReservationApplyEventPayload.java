package com.sim.reservation.data.reservation.event.payload;

import java.util.UUID;

import com.sim.reservation.data.reservation.domain.Reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationApplyEventPayload implements Payload {
	private String id;
	private Long reservationId;

	@Override
	public String getId() {
		return id;
	}

	@Builder
	private ReservationApplyEventPayload(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static ReservationApplyEventPayload from(Reservation reservation) {
		return ReservationApplyEventPayload.builder()
			.id(UUID.randomUUID().toString())
			.reservationId(reservation.getId())
			.build();
	}
}
