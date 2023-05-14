package com.sim.reservationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservationservice.dao.PerformanceInfoCustomRepository;
import com.sim.reservationservice.dao.PerformanceInfoCustomRepositoryImpl;

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
    public PerformanceInfoCustomRepository performanceInfoCustomRepository(JPAQueryFactory jpaQueryFactory) {
        return new PerformanceInfoCustomRepositoryImpl(jpaQueryFactory);
    }
}
