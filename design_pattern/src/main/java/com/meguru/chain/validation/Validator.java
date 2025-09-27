package com.meguru.chain.validation;

import com.meguru.chain.annotation.Length;
import com.meguru.chain.annotation.Max;
import com.meguru.chain.annotation.Min;

import java.lang.reflect.Field;

public class Validator {
    public void validate(Object bean) throws Exception {

        Class<?> beanClass = bean.getClass();
        for (Field field : beanClass.getDeclaredFields()) {
            field.setAccessible(true);
            ValidatorChain chain = buildValidatorChain(field);
            chain.validate(field.get(bean));
        }
    }

    ValidatorChain buildValidatorChain(Field field) {
        ValidatorChain chain = new ValidatorChain();
        Max max = field.getAnnotation(Max.class);
        if (max != null) {
            chain.addHanlder(new MaxValidatorHandler(max.value()));
        }
        Min min = field.getAnnotation(Min.class);
        if (min != null) {
            chain.addHanlder(new MinValidatorHandler(min.value()));
        }
        Length length = field.getAnnotation(Length.class);
        if (length != null) {
            chain.addHanlder(new LenthValidatorHandler(length.value()));
        }
        return chain;
    }
}
