package com.meguru.chain.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidatorContext {

    private final List<String> errorMessageList = new ArrayList<>();

    private boolean stop = false;

    private int index = 0;

    private Object value;

    private Map<String, Object> data = new HashMap<>();

    public ValidatorContext(Object value) {
        this.value = value;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public Object getValue() {
        return value;
    }

    public void appendError(String message) {
        errorMessageList.add(message);
    }

    public void stopChain() {
        this.stop = true;
    }

    public boolean shouldStop() {
        return stop;
    }

    public void doNext(Object value) {
        index++;
        this.value = value;
    }

    public int currectIndex() {
        return index;
    }

    public void throwExceptionIfNeccessary() {
        if (errorMessageList.isEmpty()) {
            return;
        }
        throw new IllegalArgumentException(errorMessageList.toString());
    }
}
