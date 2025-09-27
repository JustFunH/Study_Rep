package com.meguru.builder;

public class User {

    private final String name;

    private final int age;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static class Builder {
        private String name;
        private int age;

        private Builder() {

        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public User build() {
            User user = new User(this);
            return user;
        }

    }

}
