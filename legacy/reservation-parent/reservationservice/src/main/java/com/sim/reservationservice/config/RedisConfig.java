package com.sim.reservationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

/**
 * RedisConfig.java
 * Redis 구성
 *
 * @author sgh
 * @since 2023.04.24
 */
@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {
	private final RedisClusterConfigurationProperties clusterProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
		clusterProperties.getNodes().forEach(n -> {
			String[] url = n.split(":");
			redisConfig.clusterNode(url[0], Integer.parseInt(url[1]));
		});
		return new LettuceConnectionFactory(redisConfig);
	}

	@Primary
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		return redisTemplate;
	}

}
