package com.meguru.facade;

public class Tomcat implements ServerFacade {
    void initEngine() {
        System.out.println("初始化Tomcat引擎");
    }

    void initWeb() {
        System.out.println("加载Web应用");
    }

    @Override
    public void start() {
        initEngine();
        initWeb();
    }
}
