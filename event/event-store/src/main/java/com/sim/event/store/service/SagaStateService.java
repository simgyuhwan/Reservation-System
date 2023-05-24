package com.sim.event.store.service;

import com.sim.event.store.domain.SagaState;

public interface SagaStateService {
	void saveSagaState(SagaState sagaState);
}
