package com.meguru.chain.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidatorChain {

    private final List<ValidatorHandler> handlers = new ArrayList<>();

    public void validate(Object value) throws Exception {
        ValidatorContext context = new ValidatorContext(value);
        while (true) {
            int index = context.currectIndex();
            if (index == handlers.size())
                break;
            ValidatorHandler handler = handlers.get(index);
            handler.validate(context.getValue(), context);
            if (index == context.currectIndex()) {
                break;
            }
        }
        context.throwExceptionIfNeccessary();
    }

    public void addHanlder(ValidatorHandler handler) {
        this.handlers.add(handler);
    }
}
