// src/main/java/com/example/ecommerce/model/user/Customer.java
package com.EcommerceApplication.demo.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private String id;
    private String name;
    private double balance;

    public Customer(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void deductBalance(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}