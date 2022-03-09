package com.example.trynacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetService {
    @NacosInjected
    private NamingService namingService;

    @RequestMapping("/service")
    public String getService() {
        String instances = "";
        try {
            namingService.registerInstance("test-service", "1.1.1.1", 8080);
            List<Instance> instanceList = namingService.getAllInstances("test-service");
            for (Instance instance :
                    instanceList) {
                String instanceString = instance.getIp() + ":" + instance.getPort();
                System.out.printf(instanceString);
                instances += instanceString;
            }
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return instances;
    }
}
