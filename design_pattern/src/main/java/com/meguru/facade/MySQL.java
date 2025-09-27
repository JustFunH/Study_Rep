package com.meguru.facade;

public class MySQL implements ServerFacade {

    void initData() {
        System.out.println("初始化Mysql");
    }

    void checkLog() {
        System.out.println("校验日志");
    }

    void listenPort() {
        System.out.println("监听端口");
    }

    @Override
    public void start() {
        initData();
        checkLog();
        listenPort();
    }
}
