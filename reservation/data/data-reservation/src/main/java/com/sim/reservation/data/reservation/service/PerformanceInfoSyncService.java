package com.sim.reservation.data.reservation.service;

/**
 * 공연 정보를 예약 서비스에 동기화하는 서비스
 *
 */
public interface PerformanceInfoSyncService {

	/**
	 * 공연 정보 요청 후 performanceInfo 저장
	 */
	boolean requestAndSavePerformanceInfo(Long performanceId);

	/**
	 * 공연 정보 요청 후 performanceInfo 수정
	 */
	boolean requestAndUpdatePerformanceInfo(Long performanceId);
}
