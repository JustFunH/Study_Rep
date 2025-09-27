package com.meguru.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 总线
public class TVStation {

    private final Map<Class<? extends Event>, List<EventListener>> listenerMap = new HashMap<>();

    public void subscribe(EventListener listener, Class<? extends Event> eventClass) {
        listenerMap.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(listener);
    }

    public void publish(Event event) {
        Class<? extends Event> aClass = event.getClass();
        List<EventListener> eventListeners = listenerMap.get(aClass);
        if (eventListeners != null) {
            eventListeners.forEach(listener -> listener.onEvent(event));
        }
    }

}
