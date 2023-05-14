package com.sim.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MemberApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApiApplication.class, args);
	}

}
