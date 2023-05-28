package com.sim.notification.clients.reservation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 예약 서비스 Client
 */
@FeignClient(name = "reservation-service", path = "/api/reservations")
public interface ReservationClient {

	@GetMapping("/{reservationId}")
	ReservationInfo getReservationInfoById(@PathVariable("reservationId") Long reservationId);
}
