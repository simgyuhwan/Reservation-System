package com.reservation.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 비동기 설정
 */
@EnableAsync
@Configuration
public class AsyncConfig {
	private static final int CORE_POOL_SIZE = 5;
	private static final int MAX_POOL_SIZE = 30;
	private static final int QUEUE_CAPACITY = 50;
	private static final String THREAD_NAME_PREFIX = "defaultExecutor-";

	@Bean(name = "defaultExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(CORE_POOL_SIZE); // 기본 실행 대기 스레드 수, default 1
		executor.setMaxPoolSize(MAX_POOL_SIZE); // 동시 동작하는 최대 스레드 수, default Integer.MAX_VALUE
		executor.setQueueCapacity(QUEUE_CAPACITY); // 최대 스레드 수 초과시 최대 수용 가능한 큐 수, default Integer.MAX_VALUE
		executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
		executor.initialize();
		return executor;
	}
}
