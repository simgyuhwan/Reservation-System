package com.sim.notification.event;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

	@Override
	public void sendReservationRequest(NotificationRequestEvent notificationRequestEvent) {

	}
}
