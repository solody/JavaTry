package com.example.trynacos;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosConfigurationProperties(dataId = "my_data", type = ConfigType.YAML, autoRefreshed = true)
public class TryNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TryNacosApplication.class, args);
    }

}
