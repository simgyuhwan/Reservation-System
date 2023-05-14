package com.reservation.performanceservice.domain;

import com.reservation.common.model.BaseEntity;
import com.reservation.common.types.EventStatusType;
import com.reservation.performanceservice.event.PerformanceEvent;
import com.reservation.performanceservice.event.payload.PerformanceCreatedPayload;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
  * EventStatus.java
  * 이벤트 상태
  *
  * @author sgh
  * @since 2023.05.11
  */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventStatus extends BaseEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EventStatusType status;

    private Long performanceId;

    @Builder
    private EventStatus(String id, EventStatusType status, Long performanceId) {
        this.id = id;
        this.status = status;
        this.performanceId = performanceId;
    }

    public static EventStatus from(PerformanceEvent performanceEvent, EventStatusType statusType) {
        PerformanceCreatedPayload payload = (PerformanceCreatedPayload)performanceEvent.getPayload();
        return EventStatus.builder()
            .id(performanceEvent.getId())
            .status(statusType)
            .performanceId(payload.getPerformanceId())
            .build();
    }

    public boolean isCompleted() {
        return EventStatusType.SUCCESS.equals(status);
    }

    public void changeToCompleted() {
        status = EventStatusType.SUCCESS;
    }

    public void changeToFailed() {
        status = EventStatusType.FAIL;
    }
}
