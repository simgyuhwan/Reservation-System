package com.reservation.performances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PerformancesApplication {
	public static void main(String[] args) {
		SpringApplication.run(PerformancesApplication.class, args);
	}
}
