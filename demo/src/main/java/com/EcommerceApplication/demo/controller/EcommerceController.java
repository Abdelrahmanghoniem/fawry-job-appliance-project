// src/main/java/com/example/ecommerce/controller/EcommerceController.java
package com.EcommerceApplication.demo.controller;

import com.EcommerceApplication.demo.dto.CheckoutRequest;
import com.EcommerceApplication.demo.service.CheckoutService;
import com.EcommerceApplication.demo.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ecommerce")
public class EcommerceController {
    private final CheckoutService checkoutService;

    @Autowired
    public EcommerceController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestBody CheckoutRequest request) {
        try {
            checkoutService.checkout(request.getCustomer(), request.getCart());
            return "Checkout completed successfully";
        } catch (Exception e) {
            return "Checkout failed: " + e.getMessage();
        }
    }
}