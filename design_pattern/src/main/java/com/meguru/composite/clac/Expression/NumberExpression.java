package com.meguru.composite.clac.Expression;

public class NumberExpression implements Expression {

    private final int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
