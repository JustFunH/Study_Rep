package com.meguru.observer;

public class WeatherUpdateEvent extends BaseEvent {

    private final String info;

    public WeatherUpdateEvent(String info) {
        this.info = info;
    }

    @Override
    public Object source() {
        return info;
    }
}
