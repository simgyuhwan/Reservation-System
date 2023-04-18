package com.sim.reservationservice.application;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.reservationservice.application.mapper.PerformanceInfoMapper;
import com.sim.reservationservice.dao.PerformanceInfoRepository;
import com.sim.reservationservice.dto.request.PerformanceDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceConsumer.java
 * 공연 구독 클래스
 *
 * @author sgh
 * @since 2023.04.18
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PerformanceInfoConsumer {
    private final PerformanceInfoMapper performanceInfoMapper;
    private final PerformanceInfoRepository performanceInfoRepository;

    @Bean
    public Consumer<String> performanceConsumer() {
        return value -> {
            ObjectMapper mapper = new ObjectMapper();
            PerformanceDto performanceDto = null;
            try{
                 performanceDto = mapper.readValue(value, PerformanceDto.class);
            } catch (JsonProcessingException e) {
                log.error("공연 정보 Consumer mapping error : {}, json : {}", e.getMessage(), value);
                e.printStackTrace();
            }
            performanceInfoRepository.save(performanceInfoMapper.toEntity(performanceDto));
        };
    }
}
