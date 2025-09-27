package com.meguru.chain;

import com.meguru.chain.annotation.Length;
import com.meguru.chain.annotation.Max;
import com.meguru.chain.annotation.Min;

public class User {

    @Length(4)
    private final String name;

    @Max(100)
    @Min(108)
    private final Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

}
