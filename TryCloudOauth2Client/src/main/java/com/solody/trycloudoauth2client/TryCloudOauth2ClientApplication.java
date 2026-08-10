package com.solody.trycloudoauth2client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TryCloudOauth2ClientApplication {

    static void main(String[] args) {
        SpringApplication.run(TryCloudOauth2ClientApplication.class, args);
    }

}
