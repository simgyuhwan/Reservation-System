package com.reservation.performanceservice.domain;

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

    private static final long serialVersionUID = 519263776L;

    public static final QPerformance performance = new QPerformance("performance");

    public final com.reservation.common.model.QBaseEntity _super = new com.reservation.common.model.QBaseEntity(this);

    public final NumberPath<Integer> audienceCount = createNumber("audienceCount", Integer.class);

    public final StringPath contactPersonName = createString("contactPersonName");

    public final StringPath contactPhoneNum = createString("contactPhoneNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final ListPath<PerformanceDay, QPerformanceDay> performanceDays = this.<PerformanceDay, QPerformanceDay>createList("performanceDays", PerformanceDay.class, QPerformanceDay.class, PathInits.DIRECT2);

    public final StringPath performanceInfo = createString("performanceInfo");

    public final StringPath performanceName = createString("performanceName");

    public final StringPath performancePlace = createString("performancePlace");

    public final EnumPath<com.reservation.common.type.PerformanceType> performanceType = createEnum("performanceType", com.reservation.common.type.PerformanceType.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public final StringPath userId = createString("userId");

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

