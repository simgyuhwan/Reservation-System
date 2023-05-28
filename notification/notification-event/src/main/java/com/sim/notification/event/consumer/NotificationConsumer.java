package com.sim.notification.event.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.notification.event.NotificationService;

import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class NotificationConsumer {
	private final NotificationService notificationService;

	@Bean
	public Consumer<NotificationRequestEvent> notificationRequest() {
		return notificationService::sendMessage;
	}
}
