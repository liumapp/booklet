package com.liumapp.design.pattern.servicelocator;

/**
 * file ServiceLocator.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
public class ServiceLocator {

    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Service getService (String jndiName) {
        Service service = cache.getService(jndiName);

        if (service != null){
            return service;
        }

        InitialContext context = new InitialContext();
        Service service1 = (Service) context.lookup(jndiName);
        cache.addService(service1);
        return service1;
    }
}
