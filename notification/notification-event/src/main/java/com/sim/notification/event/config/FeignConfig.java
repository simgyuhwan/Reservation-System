package com.sim.notification.event.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.sim.notification.clients")
public class FeignConfig {
}
