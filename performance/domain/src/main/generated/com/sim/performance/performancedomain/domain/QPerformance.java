package com.sim.performance.performancedomain.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerformance is a Querydsl query type for Performance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformance extends EntityPathBase<Performance> {

    private static final long serialVersionUID = -222742878L;

    public static final QPerformance performance = new QPerformance("performance");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> audienceCount = createNumber("audienceCount", Integer.class);

    public final StringPath contactPersonName = createString("contactPersonName");

    public final StringPath contactPhoneNum = createString("contactPhoneNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<PerformanceDay, QPerformanceDay> performanceDays = this.<PerformanceDay, QPerformanceDay>createList("performanceDays", PerformanceDay.class, QPerformanceDay.class, PathInits.DIRECT2);

    public final StringPath performanceInfo = createString("performanceInfo");

    public final StringPath performanceName = createString("performanceName");

    public final StringPath performancePlace = createString("performancePlace");

    public final EnumPath<com.sim.performance.performancedomain.type.PerformanceType> performanceType = createEnum("performanceType", com.sim.performance.performancedomain.type.PerformanceType.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<com.sim.performance.performancedomain.type.RegisterStatusType> registrationStatus = createEnum("registrationStatus", com.sim.performance.performancedomain.type.RegisterStatusType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public QPerformance(String variable) {
        super(Performance.class, forVariable(variable));
    }

    public QPerformance(Path<? extends Performance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerformance(PathMetadata metadata) {
        super(Performance.class, metadata);
    }

}

