package com.sim.performance.performancedomain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Config
 */
@EnableJpaAuditing
@Configuration(proxyBeanMethods = false)
public class JpaConfig {
}
