package com.liumapp.netty.events;

import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * file CustomProtocolEventAdapter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/7
 */
@Component
public class CustomProtocolEventAdapter extends EventBus implements InitializingBean {

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractEventObserver> parsers = context.getBeansOfType(AbstractEventObserver.class);
        if (parsers.isEmpty())
            return;
        for (AbstractEventObserver abstractEventObsever : parsers.values())
            this.register(abstractEventObsever);
    }

}
