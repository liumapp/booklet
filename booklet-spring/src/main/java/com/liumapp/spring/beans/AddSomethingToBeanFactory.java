package com.liumapp.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

/**
 * file AddSomethingToBeanFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/5
 */
@Component
public class AddSomethingToBeanFactory implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 在Spring容器将所有的Bean都初始化完成之后，就会执行该方法
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //新建一个工厂
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //新建一个bean definition
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder
                .genericBeanDefinition(CustomService.class)
                .setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE)
                .getBeanDefinition();

        //注册到工厂
        factory.registerBeanDefinition("customService", beanDefinition);

        //定义一个bean post processor
        //在bean初始化后，判断这个bean如果实现了CustomBean接口，就把context注册进去
        factory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof CustomBean) {
                    GenericApplicationContext context = new GenericApplicationContext(factory);
                    ((ApplicationContextAware) bean).setApplicationContext(context);
                }
                return bean;
            }
        });

        // 再注册一个 bean post processor: AutowiredAnnotationBeanPostProcessor. 作用是搜索这个 bean 中的 @Autowired 注解, 生成注入依赖的信息.
//        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        autowiredAnnotationBeanPostProcessor.setBeanFactory(factory);
//        factory.addBeanPostProcessor(autowiredAnnotationBeanPostProcessor);

        // getBean() 时, 初始化
        CustomService customService = factory.getBean("customService", CustomService.class);
        customService.sayHello();
    }
}
