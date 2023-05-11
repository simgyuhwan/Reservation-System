package com.sim.reservationservice.domain;

import com.reservation.common.model.BaseEntity;
import com.reservation.common.types.EventStatusType;

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
 * Class 설명을 작성하세요.
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
            .status(EventStatusType.FAIL)
            .message(message)
            .build();
    }

    public boolean isSuccess() {
        return EventStatusType.SUCCESS.equals(status);
    }
}
