package com.nicole.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        //设置key的序列化类型，一般为String
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //设置Value的序列化类型
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        //设置Hash key的序列化类型，一般为String
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //设置Hash Value的序列化类型
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
