package com.meguru.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new MySQL().start();
        new Tomcat().start();

        new SpringApplication().run();
    }
}
