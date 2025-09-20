package com.meguru.sub;

import com.meguru.annotation.AutoWired;
import com.meguru.annotation.Component;
import com.meguru.annotation.PostConstruct;

@Component
public class Dog {
    @AutoWired
    private Cat cat;

    @PostConstruct
    public void init() {
        System.out.println("Cat注册完成");
        System.out.println("注册属性: " + cat);
    }
}
