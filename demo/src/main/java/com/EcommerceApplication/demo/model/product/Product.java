// src/main/java/com/example/ecommerce/model/product/Product.java
package com.EcommerceApplication.demo.model.product;

import com.EcommerceApplication.demo.model.shipping.Shippable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a product in the e-commerce system with configurable shipping and expiration properties.
 */
@Getter
@Setter
public class Product implements Shippable {
    private String id;
    private String name;
    private double price;
    private int quantity;

    // Expiration properties
    private boolean expirable;
    private LocalDate expiryDate;

    // Shipping properties
    private boolean shippable;
    private double weight;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expirable = false;
        this.shippable = false;
    }

    /**
     * Makes this product expirable with the given expiry date.
     */
    public Product makeExpirable(LocalDate expiryDate) {
        this.expirable = true;
        this.expiryDate = expiryDate;
        return this;
    }

    /**
     * Makes this product shippable with the given weight.
     */
    public Product makeShippable(double weight) {
        this.shippable = true;
        this.weight = weight;
        return this;
    }

    /**
     * Checks if the product is expired.
     */
    public boolean isExpired() {
        if (!expirable) return false;
        return LocalDate.now().isAfter(expiryDate);
    }

    // Shippable interface implementation
    @Override
    public double getWeight() {
        if (!shippable) throw new IllegalStateException("Product is not shippable");
        return weight;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }



    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isExpirable() {
        return expirable;
    }


    public boolean isShippable() {
        return shippable;
    }


    public void setWeight(double weight) {
        if (shippable) {
            this.weight = weight;
        } else {
            throw new IllegalStateException("Cannot set weight for non-shippable product");
        }


}}
