package com.sim.member.memberdomain;

import com.sim.member.clients.performanceclient.client.PerformanceClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected PerformanceClient performanceClient;
}
