package com.meguru.observer;

public interface Event {
    long timestamp();

    Object source();
}
