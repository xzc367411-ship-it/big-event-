package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
// 注解说明：在测试类上添加这个注解，单元测试执行前会先初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // 注入Spring自动配置好的Redis操作模板，专门处理String类型的键值对

    @Test
    public void testSet(){
        // 往Redis中存储一个键值对，使用StringRedisTemplate
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        // opsForValue() 用于操作Redis的String类型数据
        operations.set("username", "zhangsan");
        // 执行set命令，相当于Redis命令：SET username zhangsan
        operations.set("id","1",15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet(){
        // 从redis中获取一个键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
    }
}