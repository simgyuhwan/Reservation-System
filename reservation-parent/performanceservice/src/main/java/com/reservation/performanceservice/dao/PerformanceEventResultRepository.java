package com.reservation.performanceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservation.performanceservice.domain.PerformanceEventResult;

public interface PerformanceEventResultRepository extends JpaRepository<PerformanceEventResult, String> {
}
