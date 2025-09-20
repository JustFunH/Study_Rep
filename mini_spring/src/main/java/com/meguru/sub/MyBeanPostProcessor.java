package com.meguru.sub;

import com.meguru.annotation.Component;
import com.meguru.iteface.BeanPostProcessor;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object afterInitializeBean(Object bean, String beanName) {
        System.out.println("初始化完成" + beanName);
        return bean;
    }
}
