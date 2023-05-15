package com.sim.reservation.data.reservation.service;

/**
 * 공연 정보를 예약 서비스에 동기화하는 서비스
 *
 */
public interface PerformanceInfoSyncService {
	boolean requestAndSavePerformanceInfo(Long performanceId);
}
