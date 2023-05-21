package com.sim.performance.performancedomain.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventStatus is a Querydsl query type for EventStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventStatus extends EntityPathBase<EventStatus> {

    private static final long serialVersionUID = 1364294046L;

    public static final QEventStatus eventStatus = new QEventStatus("eventStatus");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final EnumPath<com.sim.performance.event.type.EventType> eventType = createEnum("eventType", com.sim.performance.event.type.EventType.class);

    public final StringPath id = createString("id");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath message = createString("message");

    public final NumberPath<Long> performanceId = createNumber("performanceId", Long.class);

    public final EnumPath<com.sim.performance.event.type.EventStatusType> status = createEnum("status", com.sim.performance.event.type.EventStatusType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDt = _super.updateDt;

    public QEventStatus(String variable) {
        super(EventStatus.class, forVariable(variable));
    }

    public QEventStatus(Path<? extends EventStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventStatus(PathMetadata metadata) {
        super(EventStatus.class, metadata);
    }

}

