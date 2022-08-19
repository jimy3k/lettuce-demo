package com.nicole.demo;

import com.nicole.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class LettuceDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Test
    public void contextLoads() {

        //通过Redis模板类，取得键值类型对应的操作类
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        //通过键值类型操作类，操作数据。 存、取
        valueOperations.set("name", "jimy3k");
        Object name = valueOperations.get("name");
        System.out.println(name);


    }

    /**
     * RedisTemplate 操作对象实例
     */
    @Test
    public void testUser() {

        //通过Redis模板类，取得键值类型对应的操作类
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        User user = new User();
        user.setId(2);
        user.setName("张三");
        user.setPassword("123456");

        valueOperations.set("user",user);
        User user1 = (User) valueOperations.get("user");

        System.out.println(user1);
    }

    /**
     * RedisTemplate 操作String类型数据
     */
    @Test
    public void testString() {

        //通过Redis模板类，取得键值类型对应的操作类
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("username","张三丰");
        String username = (String) valueOperations.get("username");

        System.out.println(username);
    }
}
