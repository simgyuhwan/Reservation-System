package com.reservation.performanceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performanceservice.domain.EventStatus;

public interface EventStatusRepository extends JpaRepository<EventStatus, String> {
}
