package com.car.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author  : ZNLCCY
 * @Version : 1.0.0
 * @Date    : 2023-12-20 13:54
 * @FileName: RedisConfig.java
 * @Desc    : Redis配置类
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // String类型 key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // String类型 value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // Hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // Hash类型 value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
