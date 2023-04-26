package com.sim.reservationservice.application;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * ReservationCommandServiceTest.java
 * 예약 Command Service 테스트
 *
 * @author sgh
 * @since 2023.04.26
 */
@ExtendWith(MockitoExtension.class)
class ReservationCommandServiceTest {

	@InjectMocks
	private ReservationCommandServiceImpl reservationCommandService;

}