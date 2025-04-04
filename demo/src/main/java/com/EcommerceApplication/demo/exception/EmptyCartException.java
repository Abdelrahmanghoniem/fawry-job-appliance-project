// src/main/java/com/example/ecommerce/exception/EmptyCartException.java
package com.EcommerceApplication.demo.exception;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String message) {
        super(message);
    }
}

