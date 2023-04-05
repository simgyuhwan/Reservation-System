package com.reservation.performanceservice.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.performanceservice.application.mapper.PerformanceDayMapper;
import com.reservation.performanceservice.application.mapper.PerformanceRegisterMapper;
import com.reservation.performanceservice.dao.PerformanceDayRepository;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
	private final PerformanceDayRepository performanceDayRepository;
	private final PerformanceRepository performanceRepository;
	private final PerformanceDayMapper performanceDayMapper;
	private final PerformanceRegisterMapper performanceRegisterMapper;

	@Override
	public void createPerformance(PerformanceDto registerDto) {
		Performance performance = performanceRepository.save(performanceRegisterMapper.toEntity(registerDto));
		List<PerformanceDay> performanceDays = performanceDayMapper.toPerformanceDays(registerDto, performance);
		performanceDayRepository.saveAll(performanceDays);
	}

}
