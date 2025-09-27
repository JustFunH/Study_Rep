package com.meguru.observer.spring;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class Controller {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ApplicationEventMulticaster eventMulticaster;

    @PostConstruct
    public void init() {
        System.out.println(context == eventPublisher);
        // PostConstruct 中发布事件会无法接收
    }

    @GetMapping("/demo")
    public String register(String user) {
        System.out.println(user + "注册了");
        eventPublisher.publishEvent(new RegisterEvent(user));
        return "ok";
    }

}
