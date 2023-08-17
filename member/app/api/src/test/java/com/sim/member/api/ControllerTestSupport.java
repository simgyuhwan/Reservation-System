package com.sim.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.member.memberdomain.service.MemberCommandService;
import com.sim.member.memberdomain.service.MemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        SignUpController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberCommandService memberCommandService;

    @MockBean
    protected MemberQueryService memberQueryService;
}
