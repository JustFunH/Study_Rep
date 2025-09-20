package com.meguru;

import com.meguru.spring.ApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext ioc = new ApplicationContext("com.meguru");
//        System.out.println(ioc.getBean("Cat"));
    }
}
