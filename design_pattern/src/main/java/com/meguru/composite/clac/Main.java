package com.meguru.composite.clac;

import com.meguru.composite.clac.Expression.Expression;
import com.meguru.composite.clac.Expression.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        ExpressionParser expressionParser = new ExpressionParser("1+15*(9+4+(1+5))+6");
        Expression parse = expressionParser.parse();
        System.out.println(parse.getValue());
        System.out.println(1 + 15 * (9 + 4 + (1 + 5)) + 6);
    }
}
