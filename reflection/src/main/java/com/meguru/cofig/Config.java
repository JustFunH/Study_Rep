package com.meguru.cofig;

import com.meguru.annotation.Bean;
import com.meguru.domain.Address;
import com.meguru.domain.Customer;
import com.meguru.domain.Message;

public class Config {

    @Bean
    public Address address() {
        return new Address("Wuhan Hubei", "233");
    }

    @Bean
    public Customer customer() {
        return new Customer("Meguru", "meguru.suki@fox.mail");
    }

    @Bean
    public Message message() {
        return new Message("Hi there");
    }
}
