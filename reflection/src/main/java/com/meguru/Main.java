package com.meguru;

import com.meguru.container.Container;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws Exception {
        Container container = new Container();
        container.init();
        Object serviceInstanceByClass = container.getServiceInstanceByClass(Class.forName("com.meguru.domain.Address"));
        System.out.println(serviceInstanceByClass);
        Object instance = container.createInstance(Class.forName("com.meguru.domain.Order"));
        Field address = Class.forName("com.meguru.domain.Order").getDeclaredField("address");
        address.setAccessible(true);
        System.out.println(address.get(instance));
    }

}
