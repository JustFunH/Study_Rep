package com.meguru.composite.clac.Expression;

public class MultiplyExpression extends BinaryOperatorExpression {
    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() * right.getValue();
    }
}
