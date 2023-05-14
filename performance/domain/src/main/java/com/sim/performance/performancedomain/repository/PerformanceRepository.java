package com.sim.performance.performancedomain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.performance.performancedomain.domain.Performance;

/**
 * PerformanceRepository.java
 *
 * @author sgh
 * @since 2023.04.03
 */
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
	List<Performance> findByMemberIdOrderByCreateDtDesc(Long memberId);
}
