package com.nicole.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class LettuceDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "jimy3k");
        Object name = valueOperations.get("name");
        System.out.println(name);
    }

}
