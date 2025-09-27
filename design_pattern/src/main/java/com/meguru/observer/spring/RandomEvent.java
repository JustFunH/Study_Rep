package com.meguru.observer.spring;

import org.springframework.context.ApplicationEvent;

public class RandomEvent extends ApplicationEvent {

    public RandomEvent(String source) {
        super(source);
    }

}
