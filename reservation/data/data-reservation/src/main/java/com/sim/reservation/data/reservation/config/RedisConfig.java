//package com.sim.reservation.data.reservation.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import io.lettuce.core.ClientOptions;
//import io.lettuce.core.resource.ClientResources;
//import io.lettuce.core.resource.DefaultClientResources;
//import lombok.RequiredArgsConstructor;
//
///**
// * RedisConfig.java
// * Redis 구성
// *
// * @author sgh
// * @since 2023.04.24
// */
//@Configuration
//@EnableRedisRepositories
//@RequiredArgsConstructor
//public class RedisConfig {
//	private final RedisClusterConfigurationProperties clusterProperties;
//
//	/**
//	 * Redis Cluster 구성
//	 */
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
//		clusterProperties.getNodes().forEach(n -> {
//			String[] url = n.split(":");
//			redisConfig.clusterNode(url[0], Integer.parseInt(url[1]));
//		});
//
//		ClientOptions clientOptions = ClientOptions.builder()
//			.autoReconnect(true)
//			.build();
//
//		ClientResources clientResources = DefaultClientResources.create();
//
//		return new LettuceConnectionFactory(redisConfig, LettuceClientConfiguration.builder()
//			.clientOptions(clientOptions)
//			.clientResources(clientResources)
//			.build());
//	}
//
//	/**
//	 * Redis Template
//	 */
//	@Primary
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(redisConnectionFactory());
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//		return redisTemplate;
//	}
//
//	/**
//	 * m1에서는 클러스터로 연동이 갑자기 되지 않아서 싱글 모드 설정
//	 */
//	// @Bean
//	// public RedissonClient redisClient() {
//	// 	Config config = new Config();
//	//
//	// 	// single mode
//	// 	config.useSingleServer().setAddress("redis://localhost:6379");
//	//
//	// 	RedissonClient redisson = Redisson.create(config);
//	// 	return redisson;
//	// }
//}
