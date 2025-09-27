package com.meguru.strategy;

import org.springframework.stereotype.Component;

@Component
public class DefaultCustomerService implements CustomerService {

    @Override
    public String findCustomer() {
        return "找不到客服";
    }
}
