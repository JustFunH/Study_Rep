package com.meguru.composite.clac.Expression;

public abstract class BinaryOperatorExpression implements Expression {
    Expression left;
    Expression right;

    protected BinaryOperatorExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
