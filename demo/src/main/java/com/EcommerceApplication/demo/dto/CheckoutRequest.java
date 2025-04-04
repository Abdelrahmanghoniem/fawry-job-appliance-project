package com.EcommerceApplication.demo.dto;

import com.EcommerceApplication.demo.model.cart.Cart;
import com.EcommerceApplication.demo.model.user.Customer;

/**
 * Data Transfer Object for checkout requests containing customer and cart information
 */
public class CheckoutRequest {
    private Customer customer;
    private Cart cart;

    // Getter for customer
    public Customer getCustomer() {
        return customer;
    }

    // Setter for customer
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Getter for cart
    public Cart getCart() {
        return cart;
    }

    // Setter for cart
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CheckoutRequest{" +
                "customer=" + customer +
                ", cart=" + cart +
                '}';
    }
}