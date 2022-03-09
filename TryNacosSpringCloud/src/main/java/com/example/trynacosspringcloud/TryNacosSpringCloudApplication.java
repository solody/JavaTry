package com.example.trynacosspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TryNacosSpringCloudApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TryNacosSpringCloudApplication.class, args);
        String hello = applicationContext.getEnvironment().getProperty("hello");
        System.err.println(hello);
    }

}
