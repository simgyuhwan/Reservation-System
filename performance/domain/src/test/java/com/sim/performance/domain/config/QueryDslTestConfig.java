package com.sim.performance.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.performance.performancedomain.repository.PerformanceCustomRepository;
import com.sim.performance.performancedomain.repository.PerformanceCustomRepositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * QueryDslTestConfig.java
 * QueryDSL 테스트 주입 구성 클래스
 *
 * @author sgh
 * @since 2023.04.24
 */
@Configuration
public class QueryDslTestConfig {

    @PersistenceContext
    EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public PerformanceCustomRepository performanceCustomRepository(JPAQueryFactory jpaQueryFactory) {
        return new PerformanceCustomRepositoryImpl(jpaQueryFactory);
    }
}
