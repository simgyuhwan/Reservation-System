package com.sim.event.orchestration.orchestrator;

/**
 * 오케스트레이션 사가
 */
public interface Saga {

	/**
	 * 사가 시작
	 */
	void start(Object request);

	/**
	 * 이벤트 헨들러
	 */
	void handle(Object response);

	/**
	 * 사가 종료
	 */
	void end(String id);
}
