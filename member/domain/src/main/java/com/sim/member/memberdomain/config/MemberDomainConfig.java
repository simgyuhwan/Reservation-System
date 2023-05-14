package com.sim.member.memberdomain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.sim.member.memberdomain.repository")
public class MemberDomainConfig {
}
