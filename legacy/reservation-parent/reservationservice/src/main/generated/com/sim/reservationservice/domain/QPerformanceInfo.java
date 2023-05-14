package com.sim.reservationservice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerformanceInfo is a Querydsl query type for PerformanceInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformanceInfo extends EntityPathBase<PerformanceInfo> {

    private static final long serialVersionUID = -422701099L;

    public static final QPerformanceInfo performanceInfo = new QPerformanceInfo("performanceInfo");

    public final com.reservation.common.model.QBaseEntity _super = new com.reservation.common.model.QBaseEntity(this);

    public final StringPath contactPersonName = createString("contactPersonName");

    public final StringPath contactPhoneNum = createString("contactPhoneNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath name = createString("name");

    public final NumberPath<Long> performanceId = createNumber("performanceId", Long.class);

    public final ListPath<PerformanceSchedule, QPerformanceSchedule> performanceSchedules = this.<PerformanceSchedule, QPerformanceSchedule>createList("performanceSchedules", PerformanceSchedule.class, QPerformanceSchedule.class, PathInits.DIRECT2);

    public final StringPath place = createString("place");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<com.reservation.common.types.PerformanceType> type = createEnum("type", com.reservation.common.types.PerformanceType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public QPerformanceInfo(String variable) {
        super(PerformanceInfo.class, forVariable(variable));
    }

    public QPerformanceInfo(Path<? extends PerformanceInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerformanceInfo(PathMetadata metadata) {
        super(PerformanceInfo.class, metadata);
    }

}

