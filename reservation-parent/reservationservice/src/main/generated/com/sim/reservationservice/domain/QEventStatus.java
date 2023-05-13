package com.sim.reservationservice.domain;

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

    private static final long serialVersionUID = -1034805885L;

    public static final QEventStatus eventStatus = new QEventStatus("eventStatus");

    public final com.reservation.common.model.QBaseEntity _super = new com.reservation.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath id = createString("id");

    //inherited
    public final BooleanPath isDelete = _super.isDelete;

    public final StringPath message = createString("message");

    public final EnumPath<com.reservation.common.types.EventStatusType> status = createEnum("status", com.reservation.common.types.EventStatusType.class);

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

