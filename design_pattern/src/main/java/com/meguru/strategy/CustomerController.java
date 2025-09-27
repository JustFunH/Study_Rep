package com.meguru.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private Map<UserType, CustomerService> customerServiceMap;

    @Autowired
    private DefaultCustomerService defaultCustomerService;

    // 1. 抽象逻辑
    // 2. 实现具体的逻辑
    // 3. 选择不同的策略
    // 4. 调用策略具体的逻辑
    @GetMapping("/customer/{recharge")
    public String customer(@PathVariable(value = "recharge") int recharge) {
        UserType userType = UserType.typeOf(recharge);
        return customerServiceMap.getOrDefault(userType, defaultCustomerService).findCustomer();
    }

    @Autowired
    public void setCustomerServiceMap(List<CustomerService> customerServiceList) {
        this.customerServiceMap = customerServiceList.stream()
                .filter(customerService -> customerService.getClass().isAnnotationPresent(SupportUserType.class))
                .collect(Collectors.toMap(this::findUserTypeFromService, Function.identity()));
    }

    private UserType findUserTypeFromService(CustomerService customerService) {
        SupportUserType annotation = customerService.getClass().getAnnotation(SupportUserType.class);
        return annotation.value();
    }

}
