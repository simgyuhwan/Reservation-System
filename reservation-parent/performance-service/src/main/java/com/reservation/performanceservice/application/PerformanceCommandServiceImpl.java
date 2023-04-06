package com.reservation.performanceservice.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.performanceservice.dto.request.PerformanceDto;

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
    @Override
    public List<PerformanceDto> selectPerformances(String userId) {
        return null;
    }
}
