package com.meguru.observer;

public class Main {
    // 观察者模式 : 事件产生者 -> 监听器
    // 发布订阅 : 事件产生者 -> 总线 -> 监听器
    public static void main(String[] args) throws InterruptedException {
        TVStation tvStation = new TVStation();
        WeatherStation weatherStation = new WeatherStation(tvStation);
        User tom = new User("tom", (info) -> {
            if (info.equals("晴天")) {
                System.out.println("晴天出去");
            } else {
                System.out.println("雨天待着");
            }
        });
        tvStation.subscribe(tom, WeatherUpdateEvent.class);
        weatherStation.start();
    }
}
