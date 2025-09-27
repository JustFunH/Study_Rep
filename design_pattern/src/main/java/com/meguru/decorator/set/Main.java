package com.meguru.decorator.set;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HistorySet<String> historySet = new HistorySet<>(new HashSet<>());
        // 装饰器只会增强原本的功能, 而不改变原来的功能
        HistorySet<String> historySet2 = new HistorySet<>(historySet);

        historySet2.add("a");
        historySet2.add("b");
        historySet2.add("c");

        historySet2.remove("c");
        System.out.println(historySet2.getRemoveList());
    }
}
