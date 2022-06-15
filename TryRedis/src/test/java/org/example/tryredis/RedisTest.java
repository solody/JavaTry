package org.example.tryredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {
    @Test
    void test() {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        jedis.auth("123456");
        jedis.set("test", "123455675");
        String data = jedis.get("test");
        Assertions.assertEquals("123455675", data);
        jedis.del("test");
    }
}
