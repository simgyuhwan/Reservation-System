package com.sim.event.orchestration.orchestrator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 이벤트를 전달하는 이벤트 버스
 */
public class EventBus {
	private Map<Class<?>, Consumer<?>> map;

	public EventBus() {
		map = new HashMap<>();
	}

	/**
	 * 이벤트와 핸들러 등록
	 */
	public <T> void register(Class<?> eventType, Consumer<T> handler) {
		map.putIfAbsent(eventType, handler);
	}

	/**
	 * 이벤트를 핸들러에 발행
	 */
	public <T> void publish(T event) {
		Consumer<?> handler = map.get(event.getClass());

		if(handler != null) {
			receive(event, handler);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void receive(T event, Consumer<?> handler) {
		((Consumer<T>) handler).accept(event);
	}
}
