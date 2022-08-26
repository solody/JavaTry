package org.example.tryredis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

@DataRedisTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testWrite() {
        redisTemplate.opsForSet().add("something", "Good!", "morning!");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("a", "A");
        stringStringHashMap.put("b", "B");
        stringStringHashMap.put("c", "C");
        redisTemplate.opsForHash().putAll("abc", stringStringHashMap);
        redisTemplate.opsForHash().put("bbb", "hello", "world!");
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar expireTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        expireTime.add(Calendar.DAY_OF_MONTH, 1);
        expireTime.set(Calendar.HOUR_OF_DAY, 0);
        expireTime.set(Calendar.MINUTE, 0);
        expireTime.set(Calendar.SECOND, 0);
        redisTemplate.expireAt("bbb", expireTime.getTime());
        System.out.println(redisTemplate.getExpire("bbb"));
    }
}
