package com.sim.reservationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservationservice.domain.Reservation;

/**
 * ReservationRepository.java
 * Reservation 도메인의 Repository
 *
 * @author sgh
 * @since 2023.04.27
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
