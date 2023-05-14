package com.reservation.gatewayserver.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * GlobalFilter.java
 * 공통 필터
 *
 * @author sgh
 * @since 2023.04.07
 */
@Slf4j
@Component
public class GlobalLogFilter extends AbstractGatewayFilterFactory<GlobalLogFilter.Config> {

	public GlobalLogFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			log.info("Global Filter request id : {}, method : {}, uri : {}", request.getId(), request.getMethod(), request.getURI());
			return chain.filter(exchange);
		}));
	}

	@Data
	public static class Config {
		private String message;
	}
}
