package com.sim.reservation.data.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sim.reservation.data.reservation.domain.EventStatus;
import com.sim.reservation.data.reservation.support.RepositoryTestSupport;
import com.sim.reservation.data.reservation.type.EventStatusType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Gyuhwan
 */
class EventStatusCustomRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private EventStatusRepository eventStatusRepository;
    
    @Autowired
    private EventStatusCustomRepository eventStatusCustomRepository;

    @AfterEach
    void setUp() {
        eventStatusRepository.deleteAllInBatch();
    }

    @DisplayName("성공한 이벤트는 완료된 이벤트이다.")
    @Test
    void aSuccessfulEventIsACompletedEvent() {
        // given
        String eventId = "event1";
        createEvent(eventId, EventStatusType.SUCCESS);

        // when
        boolean eventResult = eventStatusCustomRepository.isEventStatusFinalized(eventId);

        // then
        assertThat(eventResult).isTrue();
    }

    @DisplayName("실패한 이벤트는 완료된 이벤트이다.")
    @Test
    void aFailureEventIsACompletedEvent() {
        // given
        String eventId = "event1";
        createEvent(eventId, EventStatusType.FAILED);

        // when
        boolean eventResult = eventStatusCustomRepository.isEventStatusFinalized(eventId);

        // then
        assertThat(eventResult).isTrue();
    }
    @DisplayName("보류중인 이벤트는 완료된 이벤트가 아니다")
    @Test
    void pendingEventIsFalse() {
        // given
        String eventId = "event1";
        createEvent(eventId, EventStatusType.PENDING);

        // when
        boolean eventResult = eventStatusCustomRepository.isEventStatusFinalized(eventId);

        // then
        assertThat(eventResult).isFalse();
    }
    @DisplayName("잘못된 event ID를 사용하면 비완료 이벤트가 뜬다.")
    @NullAndEmptySource
    @ParameterizedTest
    void invalidEventIDExceptionOccurred(String input) {
        boolean eventResult = eventStatusCustomRepository.isEventStatusFinalized(input);

        assertThat(eventResult).isFalse();
    }

    @DisplayName("이벤트 ID로 조회할 수 있다.")
    @Test
    void canSearchByEventID() {
        // given
        String eventId = "eventId";
        createEvent(eventId, EventStatusType.SUCCESS);

        // when
        EventStatus eventStatus = eventStatusCustomRepository.findById(eventId).orElse(null);

        // then
        assert eventStatus != null;
        assertThat(eventStatus.isSuccess()).isTrue();
        assertThat(eventStatus.getId()).isEqualTo(eventId);
    }

    private void createEvent(String id, EventStatusType type) {
        EventStatus eventStatus = EventStatus.builder()
            .status(type)
            .id(id)
            .build();
        eventStatusRepository.save(eventStatus);
    }
}
