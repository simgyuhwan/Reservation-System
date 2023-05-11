package com.reservation.performanceservice.dao;

import static com.reservation.performanceservice.domain.QPerformance.*;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.QPerformance;
import com.reservation.performanceservice.types.RegisterStatusType;

import lombok.RequiredArgsConstructor;

/**
 * PerformanceCustomRepositoryImpl.java
 *
 * @author sgh
 * @since 2023.05.11
 */

@Repository
@RequiredArgsConstructor
public class PerformanceCustomRepositoryImpl implements PerformanceCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Performance> findRegisteredPerformancesByMemberId(Long memberId) {
        return queryFactory.selectFrom(performance)
            .where(memberIdEq(memberId)
                .and(isRegistrationCompleted()))
            .orderBy(performance.createDt.desc())
            .fetch();
    }

    @Override
    public boolean isRegistrationCompleted(Long performanceId) {
        return getRegisteredPerformance(performanceId) != null;
    }

    @Override
    public Optional<Performance> findPerformanceById(Long performanceId) {
        Performance performance = getRegisteredPerformance(performanceId);
        return Optional.ofNullable(performance);
    }

    @Override
    public Optional<Performance> findPendingPerformance(Long performanceId) {
        Performance performance = queryFactory.selectFrom(QPerformance.performance)
            .where(performanceIdEq(performanceId)
                .and(isRegistrationPending()))
            .orderBy(QPerformance.performance.createDt.desc())
            .fetchOne();
        return Optional.ofNullable(performance);
    }

    @Nullable
    private Performance getRegisteredPerformance(Long performanceId) {
        return queryFactory.selectFrom(QPerformance.performance)
            .where(performanceIdEq(performanceId)
                .and(isRegistrationCompleted()))
            .fetchOne();
    }

    private BooleanExpression performanceIdEq(Long performanceId) {
        return performance.id.eq(performanceId);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        Assert.notNull(memberId, "memberId is must be not null");
        return performance.memberId.eq(memberId);
    }

    private BooleanExpression isRegistrationCompleted() {
        return performance.registrationStatus.eq(RegisterStatusType.COMPLETED);
    }

    private BooleanExpression isRegistrationPending() {
        return performance.registrationStatus.eq(RegisterStatusType.PENDING);
    }
}
