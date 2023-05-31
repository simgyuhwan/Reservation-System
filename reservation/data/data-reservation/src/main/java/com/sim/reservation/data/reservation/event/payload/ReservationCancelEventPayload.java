package com.sim.reservation.data.reservation.event.payload;

import java.util.UUID;

import com.sim.reservation.data.reservation.domain.Reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelEventPayload implements Payload{
	private String id;
	private Long reservationId;

	@Override
	public String getId() {
		return id;
	}

	@Builder
	private ReservationCancelEventPayload(String id, Long reservationId) {
		this.id = id;
		this.reservationId = reservationId;
	}

	public static ReservationCancelEventPayload from(Reservation reservation) {
		return ReservationCancelEventPayload.builder()
			.id(UUID.randomUUID().toString())
			.reservationId(reservation.getId())
			.build();
	}
}
