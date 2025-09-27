package com.meguru.chain.validation;

public class MaxValidatorHandler implements ValidatorHandler {

    private final int max;

    public MaxValidatorHandler(int max) {
        this.max = max;
    }

    @Override
    public void validate(Object value, ValidatorContext context) {
        if (!(value instanceof Integer)) {
            return;
        }
        if ((Integer) value > max) {
            context.appendError(value + "不能大于" + max);
        }
        context.doNext(value);
    }
}
