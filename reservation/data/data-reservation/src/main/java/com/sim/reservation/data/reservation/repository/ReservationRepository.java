package com.sim.reservation.data.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservation.data.reservation.domain.Reservation;

/**
 * ReservationRepository.java
 * Reservation 도메인의 Repository
 *
 * @author sgh
 * @since 2023.04.27
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
