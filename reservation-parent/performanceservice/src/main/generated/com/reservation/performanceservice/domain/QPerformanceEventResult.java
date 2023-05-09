package com.reservation.performanceservice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPerformanceEventResult is a Querydsl query type for PerformanceEventResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerformanceEventResult extends EntityPathBase<PerformanceEventResult> {

    private static final long serialVersionUID = 640977175L;

    public static final QPerformanceEventResult performanceEventResult = new QPerformanceEventResult("performanceEventResult");

    public final com.reservation.common.model.QBaseEntity _super = new com.reservation.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath id = createString("id");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath message = createString("message");

    public final StringPath payload = createString("payload");

    public final EnumPath<com.reservation.common.types.EventStatusType> status = createEnum("status", com.reservation.common.types.EventStatusType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public QPerformanceEventResult(String variable) {
        super(PerformanceEventResult.class, forVariable(variable));
    }

    public QPerformanceEventResult(Path<? extends PerformanceEventResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPerformanceEventResult(PathMetadata metadata) {
        super(PerformanceEventResult.class, metadata);
    }

}

