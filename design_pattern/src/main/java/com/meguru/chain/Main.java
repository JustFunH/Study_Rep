package com.meguru.chain;


import com.meguru.chain.validation.Validator;

public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User("Tome", 107);
        Validator validator = new Validator();
        validator.validate(user);
    }
}
