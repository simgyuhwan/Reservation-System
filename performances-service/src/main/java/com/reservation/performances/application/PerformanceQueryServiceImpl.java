package com.reservation.performances.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.performances.application.mapper.PerformanceDayMapper;
import com.reservation.performances.application.mapper.PerformanceRegisterMapper;
import com.reservation.performances.dao.PerformanceDayRepository;
import com.reservation.performances.dao.PerformanceRepository;
import com.reservation.performances.domain.Performance;
import com.reservation.performances.domain.PerformanceDay;
import com.reservation.performances.dto.request.PerformanceRegisterDto;
import com.reservation.performances.error.ErrorCode;
import com.reservation.performances.error.InvalidPerformanceDateException;

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
	public void createPerformance(PerformanceRegisterDto registerDto) {
		Performance performance = performanceRepository.save(performanceRegisterMapper.toEntity(registerDto));
		List<PerformanceDay> performanceDays = registerDto.toPerformanceDays(performance);
		performanceDayRepository.saveAll(performanceDays);
	}

}
