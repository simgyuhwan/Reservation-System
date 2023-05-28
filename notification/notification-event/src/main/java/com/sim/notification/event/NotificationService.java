package com.sim.notification.event;

import com.sim.notification.event.consumer.NotificationRequestEvent;

public interface NotificationService {
	void sendMessage(NotificationRequestEvent notificationRequestEvent);
}
