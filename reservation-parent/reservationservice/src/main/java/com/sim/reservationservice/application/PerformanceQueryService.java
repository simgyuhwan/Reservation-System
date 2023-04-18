package com.sim.reservationservice.application;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

/**
 * PerformanceQueryService.java
 * 공연 Query 서비스
 *
 * @author sgh
 * @since 2023.04.18
 */
public interface PerformanceQueryService {
	List<PerformanceInfoDto> selectPerformances(PerformanceSearchDto performanceSearchDto, Pageable pageable);
}
