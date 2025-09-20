package com.meguru.domain;

public class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer() {
    }

    public void printName() {
        System.out.println("Customer name: " + name);
    }

    public void printEmail() {
        System.out.println("Customer email: " + email);
    }
}
