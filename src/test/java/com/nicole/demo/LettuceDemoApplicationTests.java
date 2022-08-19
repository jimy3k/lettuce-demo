package com.nicole.demo;

import com.nicole.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * RedisTemplate 操作String类型数据，包括多条String类型值
     */
    @Test
    public void testString() {

        //通过Redis模板类，取得键值类型对应的操作类
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("username","张三丰");
        String username = (String) valueOperations.get("username");

        System.out.println(username);

        //设置多条String 值
        Map<String,Object> map = new HashMap<>();
        map.put("password","123456");
        map.put("addr","深圳");
        map.put("sex","男");
        map.put("score","96");

        valueOperations.multiSet(map);

        //取多条String 值
        List<String> list = new ArrayList<>();
        list.add("username");
        list.add("password");
        list.add("addr");
        list.add("sex");
        list.add("score");

        List<Object> objectList = valueOperations.multiGet(list);
        assert objectList != null;
        objectList.forEach(System.out::println);

        //层级目录
        valueOperations.set("user:01:cart:item01","小米");
        Object item = valueOperations.get("user:01:cart:item01");
        System.out.println(item);

        //通用删除 key
        redisTemplate.delete("user:01:cart:item01");
    }

    /**
     * RedisTemplate 操作Hash类型数据
     */
    @Test
    public void testHash() {

        //通过Redis模板类，取得键值类型对应的操作类
        HashOperations<String,Object,Object> hashOperations = redisTemplate.opsForHash();

        //设值， 单条
        hashOperations.put("user","name","周杰伦");

        //取值， 单条
        String name = (String) hashOperations.get("user","name");

        System.out.println(name);

        //设值， 多条
        Map<String,Object> map = new HashMap<>();
        map.put("username","周杰伦");
        map.put("password","123456");
        map.put("addr","深圳");
        map.put("sex","男");
        map.put("score","96");

        hashOperations.putAll("userInfo",map);

        //取值， 多条
        List<Object> list = new ArrayList<>();
        list.add("username");
        list.add("password");
        list.add("addr");
        list.add("sex");
        list.add("score");

        List<Object> objectList = hashOperations.multiGet("userInfo",list);
        objectList.forEach(System.out::println);

        //获取整个Hash对象
        Map<Object,Object> userInfo = hashOperations.entries("userInfo");
        userInfo.forEach((k,v)-> System.out.println(k+"---->"+v));
    }

    /**
     * RedisTemplate 操作List类型数据
     */
    @Test
    public void testList() {

        //通过Redis模板类，取得键值类型对应的操作类
        ListOperations<String,Object> listOperations = redisTemplate.opsForList();

        //设值
        listOperations.leftPush("students","牛二");
        listOperations.leftPush("students","马妞");
        listOperations.leftPushAll("students","张三","李四","王五","戴森");

        listOperations.rightPush("students","哈哈儿");
        listOperations.rightPushAll("students","李铁","虎妞","郝丹");

        //设值
        List<Object> list = new ArrayList<>();
        list = listOperations.range("students",0,-1);
        assert list != null;
        list.forEach(System.out::println);
    }
}
