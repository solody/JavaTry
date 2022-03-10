package com.example.trynacosspringcloud;

import org.apache.catalina.startup.UserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(UserConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
public class TryNacosSpringCloudApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TryNacosSpringCloudApplication.class, args);
        String hello = applicationContext.getEnvironment().getProperty("hello");
        System.err.println(hello);
    }

}
