package com.liumapp.design.pattern.servicelocator;

import lombok.extern.slf4j.Slf4j;

/**
 * 为 JNDI 查询创建 InitialContext
 *
 * file InitialContext.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/6
 */
@Slf4j
public class InitialContext {

    public Object lookup(String jndiName){
        if (jndiName.equalsIgnoreCase("SERVICE1")){
            System.out.println("Looking up and creating a new Service1 object");
            return new Service1();
        } else if (jndiName.equalsIgnoreCase("SERVICE2")) {
            System.out.println("Looking up and creating a new Service2 object");
            return new Service2();
        }
        return null;
    }

}
