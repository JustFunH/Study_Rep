package com.meguru.strategy;

import org.springframework.stereotype.Component;

@Component
@SupportUserType(UserType.NORMAL)
public class NormalCustomerService implements CustomerService {
    @Override
    public String findCustomer() {
        return "无客服";
    }

}
