package com.EcommerceApplication.demo.exception;


public class ProductNotShippableException extends RuntimeException {
    public ProductNotShippableException(String message) {
        super(message);
    }
}