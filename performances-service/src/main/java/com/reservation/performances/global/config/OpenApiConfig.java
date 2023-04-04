package com.reservation.performances.global.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * OpenApiConfiog.java
 * Open API docs 설정
 *
 * @author sgh
 * @since 2023.03.17
 */
@OpenAPIDefinition(
	info = @Info(
		title = "예약 및 결제 서비스 API",
		version = "1.0",
		description = "예약 및 결제 서비스 API Docs"
	)
)
@Configuration
public class OpenApiConfig {
}
