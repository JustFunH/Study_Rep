package com.meguru.strategy;

import java.util.function.IntPredicate;

public enum UserType {

    NORMAL(recharge -> recharge > 0 && recharge <= 100),
    SMALL(recharge -> recharge > 100 && recharge <= 1000),
    BIG(recharge -> recharge > 1000);

    private final IntPredicate support;

    UserType(IntPredicate support) {
        this.support = support;
    }

    public static UserType typeOf(int recharge) {
        for (UserType value : values()) {
            if (value.support.test(recharge)) {
                return value;
            }
        }
        return null;
    }
}
