package com.reservation.performanceservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * CommonConfig.java
 * 공통 모듈 Config Scan
 *
 * @author sgh
 * @since 2023.04.21
 */
@Configuration
@ComponentScan(basePackages = {"com.reservation.config"})
public class CommonConfig {
}
