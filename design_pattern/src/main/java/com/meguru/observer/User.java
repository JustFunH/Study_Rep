package com.meguru.observer;

import java.util.function.Consumer;

public class User implements EventListener {

    private final String name;

    private final Consumer<String> consumer;

    public User(String name, Consumer<String> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public void receiveInfo(String info) {
        consumer.accept(info);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof WeatherUpdateEvent) {
            receiveInfo(event.source().toString());
        }
    }
}
