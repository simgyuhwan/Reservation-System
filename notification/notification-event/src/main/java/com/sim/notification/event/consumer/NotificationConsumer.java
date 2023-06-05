package com.sim.notification.event.consumer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sim.notification.event.NotificationService;

import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class NotificationConsumer {
	private final NotificationService notificationService;

	@Bean
	public Function<ReservationApplyEvent, NotificationCompleteEvent> notificationRequest() {
		return event -> {
			notificationService.sendMessage(event);
			return NotificationCompleteEvent.from(event);
		};
	}

	@Bean
	public Function<ReservationCancelEvent, NotificationCompleteEvent> reservationCancelNotification() {
		return event -> {
			notificationService.sendMessage(event);
			return NotificationCompleteEvent.from(event);
		};
	}
}
