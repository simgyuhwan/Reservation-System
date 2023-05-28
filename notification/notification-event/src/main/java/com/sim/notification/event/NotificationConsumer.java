package com.sim.notification.event;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class NotificationConsumer {

	@Bean
	public Consumer<NotificationRequestEvent> notificationRequest() {
		return a -> {
			System.out.println(a);
			System.out.println("=======================");
		};
	}
}
