package com.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration(proxyBeanMethods = false)
public class ConverterConfig {

	@Bean
	public Gson gson() {
		return new Gson();
	}
}
