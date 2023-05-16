package com.sim.performance.performancedomain.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerformanceDay is a Querydsl query type for PerformanceDay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformanceDay extends EntityPathBase<PerformanceDay> {

    private static final long serialVersionUID = -8537702L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerformanceDay performanceDay = new QPerformanceDay("performanceDay");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final DatePath<java.time.LocalDate> end = createDate("end", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final QPerformance performance;

    public final DatePath<java.time.LocalDate> start = createDate("start", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> time = createTime("time", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public QPerformanceDay(String variable) {
        this(PerformanceDay.class, forVariable(variable), INITS);
    }

    public QPerformanceDay(Path<? extends PerformanceDay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerformanceDay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerformanceDay(PathMetadata metadata, PathInits inits) {
        this(PerformanceDay.class, metadata, inits);
    }

    public QPerformanceDay(Class<? extends PerformanceDay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.performance = inits.isInitialized("performance") ? new QPerformance(forProperty("performance")) : null;
    }

}

