package com.nicole.demo;

import com.nicole.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
        listOperations.leftPushAll("students", "张三", "李四", "王五", "戴森");

        listOperations.rightPush("students", "哈哈儿");
        listOperations.rightPushAll("students", "李铁", "虎妞", "郝丹");

        //取值,全部
        List<Object> list = new ArrayList<>();
        list = listOperations.range("students", 0, -1);
        assert list != null;
        list.forEach(System.out::println);

        //取值,根据索引
        Object obj = listOperations.index("students", 0);
        System.out.println(obj);
    }

    /**
     * RedisTemplate 操作Set类型数据
     */
    @Test
    public void testSet() {

        //通过Redis模板类，取得键值类型对应的操作类
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();

        //设值
        setOperations.add("Sets", "牛二", "张三", "李四", "王五", "戴森");

        //取值
        long size = setOperations.size("Sets");
        System.out.println(size);

        Set<Object> set = setOperations.members("Sets");

        assert set != null;
        set.forEach(System.out::println);

        //删除
        long dele = setOperations.remove("Sets", "戴森");
        System.out.println(dele);
    }

    /**
     * RedisTemplate 操作SortedSet类型数据
     */
    @Test
    public void testSortedSet() {

        //通过Redis模板类，取得键值类型对应的操作类
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

        //设值
        zSetOperations.add("ZSets", "牛二", 12D);
        zSetOperations.add("ZSets", "张三", 13D);
        zSetOperations.add("ZSets", "李四", 14D);
        zSetOperations.add("ZSets", "王五", 15D);
        zSetOperations.add("ZSets", "戴森", 16D);

        //取值
        long size = zSetOperations.size("ZSets");
        System.out.println(size);

        Set<Object> set = zSetOperations.range("ZSets", 0, -1);

        assert set != null;
        set.forEach(System.out::println);

        //删除
        long dele = zSetOperations.remove("ZSets", "戴森");
        System.out.println(dele);
    }

    /**
     * RedisTemplate 查询所有key
     */
    @Test
    public void testKeys() {
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        keys.forEach(System.out::println);
    }

    /**
     * RedisTemplate 操作失效时间
     */
    @Test
    public void testExpired() {
        //设值时设置失效时间
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("code", "123456", 30, TimeUnit.SECONDS);

        //给已存在值设置失效时间
        redisTemplate.expire("code", 18, TimeUnit.SECONDS);
        //查询失效时间
        long time = redisTemplate.getExpire("code", TimeUnit.SECONDS);
        System.out.println(time);
    }
}
