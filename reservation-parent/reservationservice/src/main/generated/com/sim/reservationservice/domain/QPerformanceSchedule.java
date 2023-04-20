package com.sim.reservationservice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerformanceSchedule is a Querydsl query type for PerformanceSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformanceSchedule extends EntityPathBase<PerformanceSchedule> {

    private static final long serialVersionUID = -342402498L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerformanceSchedule performanceSchedule = new QPerformanceSchedule("performanceSchedule");

    public final NumberPath<Integer> availableSeats = createNumber("availableSeats", Integer.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPerformanceInfo performanceInfo;

    public final NumberPath<Integer> remainingSeats = createNumber("remainingSeats", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public QPerformanceSchedule(String variable) {
        this(PerformanceSchedule.class, forVariable(variable), INITS);
    }

    public QPerformanceSchedule(Path<? extends PerformanceSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerformanceSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerformanceSchedule(PathMetadata metadata, PathInits inits) {
        this(PerformanceSchedule.class, metadata, inits);
    }

    public QPerformanceSchedule(Class<? extends PerformanceSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.performanceInfo = inits.isInitialized("performanceInfo") ? new QPerformanceInfo(forProperty("performanceInfo")) : null;
    }

}

