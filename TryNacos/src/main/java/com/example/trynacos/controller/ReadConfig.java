package com.example.trynacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NacosPropertySource(dataId = "my", autoRefreshed = true, type = ConfigType.PROPERTIES)
public class ReadConfig {

    @NacosInjected
    private ConfigService configService;

    @NacosValue("${hello}")
    private String hello;

    @RequestMapping("/")
    public String read() throws NacosException {
        String content = configService.getConfig("my", "DEFAULT_GROUP", 2000);
        return hello;
    }
}
