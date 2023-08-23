package com.sim.reservation.boot.api;

import com.google.gson.Gson;
import com.sim.reservation.boot.service.ReservationSearchService;
import com.sim.reservation.boot.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    ReservationController.class,
    ReservationSearchController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @MockBean
    protected ReservationService reservationService;

    @MockBean
    protected ReservationSearchService reservationSearchService;

}
