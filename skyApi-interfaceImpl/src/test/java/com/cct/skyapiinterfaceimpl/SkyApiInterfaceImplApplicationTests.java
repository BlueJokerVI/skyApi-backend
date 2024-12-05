package com.cct.skyapiinterfaceimpl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SkyApiInterfaceImplApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void contextLoads() {
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        System.out.println(k1);
    }

}
