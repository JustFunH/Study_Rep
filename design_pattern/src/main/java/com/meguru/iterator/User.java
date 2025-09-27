package com.meguru.iterator;

import java.util.Iterator;

public class User implements Iterable<String> {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Iterator<String> iterator() {
        return new UserIterator();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    class UserIterator implements Iterator<String> {

        int count = 2;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public String next() {
            count--;
            if (count == 1) {
                return User.this.name;
            }
            if (count == 0) {
                return User.this.age + "";
            }
            return null;
        }
    }
}
