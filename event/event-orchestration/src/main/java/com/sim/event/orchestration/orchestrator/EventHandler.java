package com.sim.event.orchestration.orchestrator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventHandler {
	private Map<Class<?>, Consumer<?>> map;

	public EventHandler() {
		map = new HashMap<>();
	}

	public <T> void register(Class<?> eventType, Consumer<T> handler) {
		map.putIfAbsent(eventType, handler);
	}

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
