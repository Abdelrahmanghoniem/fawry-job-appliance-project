package com.EcommerceApplication.demo.exception;


public class ProductExpiredException extends RuntimeException {
    public ProductExpiredException(String message) {
        super(message);
    }
}
