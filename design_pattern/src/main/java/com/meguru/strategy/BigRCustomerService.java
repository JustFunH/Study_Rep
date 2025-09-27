package com.meguru.strategy;

import org.springframework.stereotype.Component;

@Component
@SupportUserType(UserType.BIG)
public class BigRCustomerService implements CustomerService {
    @Override
    public String findCustomer() {
        return "专属客服";
    }

}
