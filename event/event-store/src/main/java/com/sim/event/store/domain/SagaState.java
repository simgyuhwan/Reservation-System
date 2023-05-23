package com.sim.event.store.domain;

import com.sim.event.store.type.SagaStep;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SagaState extends BaseEntity{

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private SagaStep sagaStep;

}
