package com.sim.reservation.data.reservation.domain;

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

    private static final long serialVersionUID = -1301770595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerformanceSchedule performanceSchedule = new QPerformanceSchedule("performanceSchedule");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> availableSeats = createNumber("availableSeats", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final QPerformanceInfo performanceInfo;

    public final NumberPath<Integer> remainingSeats = createNumber("remainingSeats", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

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

