package com.meguru.chain.validation;

public class LenthValidatorHandler implements ValidatorHandler {
    private final int length;

    public LenthValidatorHandler(int length) {
        this.length = length;
    }

    @Override
    public void validate(Object value, ValidatorContext context) {
        if (!(value instanceof String)) {
            return;
        }
        if (((String) value).length() != length) {
            context.appendError(value + "长度不为" + length);
        }
        context.doNext(value);
    }
}
