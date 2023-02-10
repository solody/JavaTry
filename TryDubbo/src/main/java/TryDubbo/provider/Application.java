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
