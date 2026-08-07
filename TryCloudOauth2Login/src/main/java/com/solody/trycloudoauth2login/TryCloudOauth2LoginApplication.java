package com.solody.trycloudoauth2login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TryCloudOauth2LoginApplication {

    static void main(String[] args) {
        SpringApplication.run(TryCloudOauth2LoginApplication.class, args);
    }

}
