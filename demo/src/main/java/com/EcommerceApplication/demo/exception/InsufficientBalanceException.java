// src/main/java/com/example/ecommerce/exception/InsufficientBalanceException.java
package com.EcommerceApplication.demo.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}