package com.sim.reservationservice.event.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.reservationservice.application.mapper.PerformanceInfoMapper;
import com.sim.reservationservice.dao.PerformanceInfoRepository;

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
public class PerformanceConsumer {
	private final PerformanceInfoMapper performanceInfoMapper;
	private final PerformanceInfoRepository performanceInfoRepository;

	// @Bean
	// public Consumer<String> performanceConsumer() {
	// 	return value -> {
	// 		try {
	// 			PerformanceDto performanceDto = new ObjectMapper().readValue(value, PerformanceDto.class);
	// 			performanceInfoRepository.save(performanceInfoMapper.toEntity(performanceDto));
	// 		} catch (JsonProcessingException e) {
	// 			log.error("공연 정보 Consumer mapping error : {}, json : {}", e.getMessage(), value);
	// 			e.printStackTrace();
	// 		}
	// 	};
	// }

	@Bean
	public Consumer<String> performanceCreatedConsumer() {
		return value -> {
			log.info(value);
		};
	}
}
