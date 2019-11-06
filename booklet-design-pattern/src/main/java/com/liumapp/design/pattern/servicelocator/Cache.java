package com.liumapp.design.pattern.servicelocator;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * file Cache.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@Slf4j
class Cache {

    private Map<String, Service> services;

    public Cache(){
        services = new ConcurrentHashMap<>();
    }

    public Service getService(String serviceName){
        for (Map.Entry<String, Service> service : services.entrySet()) {
            if (service.getValue().getName().equalsIgnoreCase(serviceName)) {
                log.info("returning cached service : " + service.getValue() + " object");
                return service.getValue();
            }
        }
        return null;
//        throw new UnsupportedOperationException("not find service in cache: " + serviceName);
    }

    public void addService(Service newService){
        boolean exists = false;
        for (Map.Entry<String, Service> service : services.entrySet()) {
            if (service.getValue().getName().equalsIgnoreCase(newService.getName())) {
                exists = true;
            }
        }
        if (!exists) {
            services.put(newService.getName(), newService);
        }
    }

}
