package com.meguru.iterator;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        User tom = new User("tom", 11);
        User jerry = new User("jerry", 20);
        userList.add(tom);
        userList.add(jerry);
        for (String s : tom) {
            System.out.println(s);
        }
    }
}

