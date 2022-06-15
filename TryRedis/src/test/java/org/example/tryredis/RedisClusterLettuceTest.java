package org.example.tryredis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedisClusterLettuceTest {
    @Test
    void test() throws Exception {
        RedisURI lettuceUri = RedisURI.create("localhost", 6379);
        lettuceUri.setPassword("mobzone2019");

        // RedisClusterClient.create() can accept multiple RedisURI of nodes,
        // but single node also works fine,
        // Lettuce can automatically detect all node in the cluster.
        RedisClusterClient clusterClient = RedisClusterClient.create(lettuceUri);

        GenericObjectPoolConfig exchangePoolConfig = new GenericObjectPoolConfig();
        exchangePoolConfig.setMaxTotal(100);

        GenericObjectPool<StatefulRedisClusterConnection<String, String>>  syncPool = ConnectionPoolSupport.createGenericObjectPool(
                ()->clusterClient.connect(),
                exchangePoolConfig,
                true
        );

        StatefulRedisClusterConnection<String, String> connection = syncPool.borrowObject();

        RedisAdvancedClusterCommands<String, String> commands = connection.sync();

        String valueOf123 = "my jedis cluster test";
        String valueOf456 = "my jedis cluster test456";
        String valueOf789 = "my jedis cluster test789";
        String valueOf1234567890 = "my jedis cluster test1234567890";
        commands.set("123", valueOf123);
        Assertions.assertEquals(valueOf123, commands.get("123"));
        commands.set("456", valueOf456);
        Assertions.assertEquals(valueOf456, commands.get("456"));
        commands.set("789", valueOf789);
        Assertions.assertEquals(valueOf789, commands.get("789"));
        commands.set("1234567890", valueOf1234567890);
        Assertions.assertEquals(valueOf1234567890, commands.get("1234567890"));

        Assertions.assertEquals(1L, commands.exists("123"));

        connection.close();
        clusterClient.shutdown();
    }
}
