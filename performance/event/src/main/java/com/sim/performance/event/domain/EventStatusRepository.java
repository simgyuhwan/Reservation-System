package com.sim.performance.performancedomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.performance.performancedomain.domain.EventStatus;

public interface EventStatusRepository extends JpaRepository<EventStatus, String> {
}
