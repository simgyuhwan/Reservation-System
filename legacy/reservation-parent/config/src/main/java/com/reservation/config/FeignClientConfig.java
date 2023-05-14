package com.reservation.config;

import static java.util.concurrent.TimeUnit.*;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

/**
 * FeignClientConfig.java
 * FeignClient 관련 Configuration
 *
 * @author sgh
 * @since 2023.05.04
 */
@EnableFeignClients(basePackages = {"com.reservation.common.client"})
@Configuration(proxyBeanMethods = false)
public class FeignClientConfig {

    /**
     * 기본 재시작 값
     * 0.1초로 시작해 최대 3초 간격, 최대 5번 시도
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100L, SECONDS.toMillis(3L), 5);
    }
}
