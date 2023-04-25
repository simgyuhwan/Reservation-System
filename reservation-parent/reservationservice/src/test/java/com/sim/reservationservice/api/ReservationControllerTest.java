package com.sim.reservationservice.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

/**
 * ReservationControllerTest.java
 * 예약 관련 테스트
 *
 * @author sgh
 * @since 2023.04.25
 */
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
	private final String RESERVATION_BASE_URL = "/api/reservations";
	private Gson gson;
	private MockMvc mockMvc;

	@InjectMocks
	private ReservationController reservationController;

	@BeforeEach
	void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
	}

}