package com.reservation.performanceservice.application;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.NoContentException;

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
public class PerformanceCommandServiceImpl implements PerformanceCommandService{
    private final PerformanceRepository performanceRepository;
    private final PerformanceDtoMapper performanceDtoMapper;

    @Override
    public List<PerformanceDto> selectPerformances(String userId) {
        List<Performance> performances = findByUserId(userId);
        return performances.stream()
            .map(performanceDtoMapper::toDto)
            .collect(toList());
    }

    private List<Performance> findByUserId(String userId) {
        List<Performance> performances = performanceRepository.findByUserIdOrderByCreateDtDesc(userId);
        if(performances.isEmpty()) {
            throw new NoContentException();
        }
        return performances;
    }
}
