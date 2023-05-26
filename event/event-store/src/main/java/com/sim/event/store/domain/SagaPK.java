package com.sim.event.store.domain;

import java.io.Serializable;

import com.sim.event.store.type.SagaStep;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class SagaPK implements Serializable {
	private String id;

	@Enumerated(EnumType.STRING)
	private SagaStep sagaStep;

	public SagaPK(String id, SagaStep sagaStep) {
		this.id = id;
		this.sagaStep = sagaStep;
	}

	public SagaPK() {
	}
}
