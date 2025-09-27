package com.meguru.composite.clac.Expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ExpressionParser {

    private final String infixExpression;
    int point = 0;

    public ExpressionParser(String infixExpression) {
        this.infixExpression = infixExpression;
    }

    // 中缀表达式转后缀表达式
    public List<String> toSuffix() {
        List<String> suffix = new ArrayList<>();
        LinkedList<String> stack = new LinkedList<>();

        while (point < infixExpression.length()) {
            char c = infixExpression.charAt(point);
            if (c == '(') {
                // 左括号直接入辅助栈
                stack.addLast(c + "");
            } else if (c == ')') {
                // 右括号在辅助栈中找到左括号, 经过的元素入结果栈
                while (!stack.getLast().equals("(")) {
                    suffix.add(stack.removeLast());
                }
                stack.removeLast();
            } else if (c == '*' || c == '/') {
                // 运算符让辅助栈顶优先级大于等于自己的运算符入结果栈( */ > +-)
                while ((!stack.isEmpty()) && (stack.getLast().equals('*')) || stack.getLast().equals('/')) {
                    suffix.add(stack.removeLast());
                }
                stack.addLast(c + "");
            } else if (c == '+' || c == '-') {
                while (topIsOperator(stack)) {
                    suffix.add(stack.removeLast());
                }
                stack.addLast(c + "");
            } else if (Character.isDigit(c)) {
                // 操作数直接入结果栈
                StringBuilder sb = new StringBuilder();
                while (point < infixExpression.length() && Character.isDigit(infixExpression.charAt(point))) {
                    sb.append(infixExpression.charAt(point));
                    point++;
                }
                point--;
                suffix.add(sb.toString());
            } else {
                throw new IllegalArgumentException();
            }
            point++;
        }

        while (!stack.isEmpty()) {
            suffix.add(stack.removeLast());
        }

        return suffix;
    }

    public Expression parse() {
        List<String> suffix = this.toSuffix();
        LinkedList<Expression> stack = new LinkedList<>();
        for (String item : suffix) {
            if (item.equals("+")) {
                // 后缀表达式中, 栈顶是右子树
                Expression right = stack.removeLast();
                stack.addLast(new AddExpression(stack.removeLast(), right));
            } else if (item.equals("-")) {
                Expression right = stack.removeLast();
                stack.addLast(new SubExpression(stack.removeLast(), right));
            } else if (item.equals("*")) {
                Expression right = stack.removeLast();
                stack.addLast(new MultiplyExpression(stack.removeLast(), right));
            } else if (item.equals("/")) {
                Expression right = stack.removeLast();
                stack.addLast(new DivExpression(stack.removeLast(), right));
            } else {
                int value = Integer.parseInt(item);
                stack.addLast(new NumberExpression(value));
            }
        }
        return stack.getLast();
    }

    private boolean topIsOperator(LinkedList<String> stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return Set.of("+", "-", "*", "/").contains(stack.getLast());
    }
}
