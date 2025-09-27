package com.meguru.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RandomEventListener {

    @EventListener
    public void onRandomEvent(RandomEvent randomEvent) {
        System.out.println(randomEvent.getSource());
    }
}
