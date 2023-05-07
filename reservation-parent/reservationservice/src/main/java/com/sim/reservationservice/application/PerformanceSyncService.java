package com.sim.reservationservice.application;

/**
 * 공연 정보를 예약 서비스에 동기화하는 서비스
 *
 */
public interface PerformanceSyncService {
	boolean requestAndSavePerformanceInfo(Long performanceId);
}
