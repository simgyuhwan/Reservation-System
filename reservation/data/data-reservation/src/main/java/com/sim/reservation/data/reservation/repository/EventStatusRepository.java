package com.sim.reservation.data.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservation.data.reservation.domain.EventStatus;

/**
 * EventStatusRepository.java
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface EventStatusRepository extends JpaRepository<EventStatus, String> {
}
