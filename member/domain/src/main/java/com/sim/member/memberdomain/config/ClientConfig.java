package com.sim.member.memberdomain.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(basePackages = "com.sim.member.clients")
public class ClientConfig {
}
