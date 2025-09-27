package com.meguru.composite.clac.Expression;

public class SubExpression extends BinaryOperatorExpression {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int getValue() {
        return left.getValue() + right.getValue();
    }
}
