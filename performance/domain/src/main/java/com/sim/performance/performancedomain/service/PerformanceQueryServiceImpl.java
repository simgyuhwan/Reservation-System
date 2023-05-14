package com.sim.performance.performancedomain.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.error.ErrorMessage;
import com.sim.performance.performancedomain.error.NoContentException;
import com.sim.performance.performancedomain.error.NotPendingPerformanceException;
import com.sim.performance.performancedomain.error.PerformanceNotFoundException;
import com.sim.performance.performancedomain.mapper.PerformanceDtoMapper;
import com.sim.performance.performancedomain.repository.PerformanceCustomRepository;
import com.sim.performance.performancedomain.repository.PerformanceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceCommandServiceImpl.java
 * 공연 Command 관련 서비스 구현체
 *
 * @author sgh
 * @since 2023.04.06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
    private final PerformanceDtoMapper performanceDtoMapper;
    private final PerformanceCustomRepository performanceCustomRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    public List<PerformanceDto> selectPerformances(Long memberId) {
        List<Performance> performances = findRegisteredPerformanceByMemberId(memberId);
        return performances.stream()
            .map(performanceDtoMapper::toDto)
            .collect(toList());
    }

    @Override
    public PerformanceDto selectPerformanceById(Long performanceId) {
        Performance performance = findRegisteredPerformanceById(performanceId);
        return performanceDtoMapper.toDto(performance);
    }

    @Override
    public PerformanceDto selectPendingPerformanceById(Long performanceId) {
        Performance performance = findPendingPerformanceById(performanceId);
        return performanceDtoMapper.toDto(performance);
    }

    @Override
    public PerformanceStatusDto getPerformanceStatusByPerformanceId(Long performanceId) {
        Performance performance = findPerformanceById(performanceId);
        return PerformanceStatusDto.from(performance);
    }

    private List<Performance> findRegisteredPerformanceByMemberId(Long memberId) {
        List<Performance> performances = performanceCustomRepository.findRegisteredPerformancesByMemberId(memberId);
        if(performances.isEmpty()) {
            throw new NoContentException(ErrorMessage.NO_CONTENT_MESSAGE, memberId);
        }
        return performances;
    }

    private Performance findRegisteredPerformanceById(Long performanceId) {
        return performanceCustomRepository.findPerformanceById(performanceId)
            .orElseThrow(() -> new PerformanceNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
    }

    private Performance findPendingPerformanceById(Long performanceId) {
        return performanceCustomRepository.findPendingPerformance(performanceId)
            .orElseThrow(() -> new NotPendingPerformanceException(ErrorMessage.NOT_PENDING_PERFORMANCE, performanceId));
    }

    private Performance findPerformanceById(Long performanceId) {
        return performanceRepository.findById(performanceId)
            .orElseThrow(() -> new PerformanceNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
    }
}
