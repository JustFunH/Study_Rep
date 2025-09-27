package com.meguru.chain.validation;

public interface ValidatorHandler {

    void validate(Object value, ValidatorContext context);
}
