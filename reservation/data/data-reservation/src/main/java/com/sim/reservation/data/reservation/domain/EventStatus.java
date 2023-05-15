package com.sim.reservation.data.reservation.domain;

import com.sim.reservation.data.reservation.type.EventStatusType;

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
 * 이벤트 상태 클래스
 *
 * @author sgh
 * @since 2023.05.11
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventStatus extends BaseEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EventStatusType status;

    private String message;

    @Builder
    private EventStatus(String id, EventStatusType status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

    public static EventStatus createSuccessEvent(String id) {
        return EventStatus.builder()
            .id(id)
            .status(EventStatusType.SUCCESS)
            .build();
    }

    public static EventStatus createFailEvent(String id, String message) {
        return EventStatus.builder()
            .id(id)
            .status(EventStatusType.FAILED)
            .message(message)
            .build();
    }

    public static EventStatus createStartEvent(String id) {
        return EventStatus.builder()
            .id(id)
            .status(EventStatusType.PENDING)
            .build();
    }

    public void changeSuccess() {
        this.status = EventStatusType.SUCCESS;
    }

    public void changeFailed() {
        this.status = EventStatusType.FAILED;
    }

    public boolean isSuccess() {
        return EventStatusType.SUCCESS.equals(status);
    }
}
