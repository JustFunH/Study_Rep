package com.meguru.observer.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GiftService {

    @EventListener
    public void onRegisterEvent(RegisterEvent registerEvent) {
        String user = registerEvent.getUser();
        System.out.println("给" + user + "发礼包了");
    }
}
