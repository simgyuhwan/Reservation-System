package com.sim.reservationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.reservationservice.domain.EventStatus;

/**
 * EventStatusRepository.java
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface EventStatusRepository extends JpaRepository<EventStatus, String> {
}
