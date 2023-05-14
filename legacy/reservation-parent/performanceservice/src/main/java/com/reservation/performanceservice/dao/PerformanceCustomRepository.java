package com.reservation.performanceservice.dao;

import java.util.List;
import java.util.Optional;

import com.reservation.performanceservice.domain.Performance;

/**
 * PerformanceInfoCustomRepository.java
 * 공연 QueryDsl 관련 Repository
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface PerformanceCustomRepository {
	List<Performance> findRegisteredPerformancesByMemberId(Long memberId);

	boolean isRegistrationCompleted(Long performanceId);

	Optional<Performance> findPerformanceById(Long performanceId);

	Optional<Performance> findPendingPerformance(Long performanceId);

}
