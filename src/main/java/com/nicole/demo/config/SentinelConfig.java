package com.nicole.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

/*
@Configuration
public class SentinelConfig {

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration()
                // Redis 主节点
                .master("mymaster")
                // 哨兵节点
                .sentinel("192.168.10.100",23679)
                .sentinel("192.168.10.101",23679)
                .sentinel("192.168.10.102",23679);
        sentinel.setPassword("jimy3k0519");
        return sentinel;
    }
}
*/
