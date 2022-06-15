package org.example.tryredis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class RedisClusterJedisTest {

    @Test
    void test() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.246.1", 6379));
        // It works fine even there is only one node is specified,
        // nodes of the cluster will be detected automatically.
        /*
        nodes.add(new HostAndPort("192.168.246.10", 6379));
        nodes.add(new HostAndPort("192.168.246.11", 6379));
        */
        JedisCluster cluster = new JedisCluster(nodes);

        String valueOf123 = "my jedis cluster test";
        String valueOf456 = "my jedis cluster test456";
        String valueOf789 = "my jedis cluster test789";
        String valueOf1234567890 = "my jedis cluster test1234567890";
        cluster.set("123", valueOf123);
        Assertions.assertEquals(valueOf123, cluster.get("123"));
        cluster.set("456", valueOf456);
        Assertions.assertEquals(valueOf456, cluster.get("456"));
        cluster.set("789", valueOf789);
        Assertions.assertEquals(valueOf789, cluster.get("789"));
        cluster.set("1234567890", valueOf1234567890);
        Assertions.assertEquals(valueOf1234567890, cluster.get("1234567890"));

        cluster.close();
    }

}
