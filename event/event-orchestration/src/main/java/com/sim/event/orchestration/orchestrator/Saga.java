package com.sim.event.orchestration.orchestrator;

import com.sim.event.orchestration.event.ReservationApplyRequest;

public interface Saga {
	void startSaga(ReservationApplyRequest request);

	void response(Object response);

	void complete(String id);
}
