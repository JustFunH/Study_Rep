package com.meguru.chain.validation;

public class MinValidatorHandler implements ValidatorHandler {

    private final int min;

    public MinValidatorHandler(int min) {
        this.min = min;
    }

    @Override
    public void validate(Object value, ValidatorContext context) {
        if (!(value instanceof Integer)) {
            return;
        }
        if ((Integer) value < min) {
            context.appendError(value + "不能小于" + min);
        }
        context.doNext(value);
    }
}
