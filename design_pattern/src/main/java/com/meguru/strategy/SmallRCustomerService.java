package com.meguru.strategy;

import org.springframework.stereotype.Component;

@Component
@SupportUserType(UserType.SMALL)
public class SmallRCustomerService implements CustomerService {
    @Override
    public String findCustomer() {
        return "普通客服";
    }

}
