# Try Dubbo


## zookeeper
Dubbo 依赖 zookeeper 作为服务注册中心，所以首先用 `docker-compose.yml` 运行一个 zookeeper 容器实例。

## 注册服务提供者
```java
package TryDubbo.provider;

import TryDubbo.api.GreetingsService;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

public class Application {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());

        DubboBootstrap.getInstance()
                .application("first-dubbo-provider")
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .protocol(new ProtocolConfig("dubbo", -1))
                .service(service)
                .start()
                .await();
    }
}
```


## 消费服务
```java
package TryDubbo.client;

import java.io.IOException;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import TryDubbo.api.GreetingsService;

public class Application {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws IOException {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class);

        DubboBootstrap.getInstance()
                .application("first-dubbo-consumer")
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .reference(reference)
                .start();

        GreetingsService service = reference.get();
        String message = service.sayHi("dubbo");
        System.out.println("Receive result ======> " + message);
        System.in.read();
        System.exit(0);
    }

}
```


> [官方入門文檔](https://cn.dubbo.apache.org/zh-cn/docs3-v2/java-sdk/quick-start/)