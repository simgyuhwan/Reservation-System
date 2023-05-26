package com.sim.event.store.domain;

import com.sim.event.store.type.SagaStep;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SagaState extends BaseEntity{

	@EmbeddedId
	private SagaPK sagaPK;

	private SagaState(String id, SagaStep sagaStep) {
		this.sagaPK = new SagaPK(id, sagaStep);
	}

	public static SagaState of(String id, SagaStep sagaStep) {
		return new SagaState(id, sagaStep);
	}
}
