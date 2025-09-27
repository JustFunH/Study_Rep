package com.meguru.composite.clac.Expression;

public class DivExpression extends BinaryOperatorExpression {
    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() / right.getValue();
    }
}
