package com.solody.trycloudauthorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TryCloudAuthorizationServerApplication {

    static void main(String[] args) {
        SpringApplication.run(TryCloudAuthorizationServerApplication.class, args);
    }

}
