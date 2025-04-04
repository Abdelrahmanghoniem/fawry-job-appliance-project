// src/main/java/com/example/ecommerce/model/CartItem.java
package com.EcommerceApplication.demo.model.cart;

import com.EcommerceApplication.demo.model.product.Product;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an item in the shopping cart with product and quantity information
 */
@Getter
@Setter
public class CartItem {
    /**
     * -- GETTER --
     *  Calculates the total price for this cart item
     *
     */ // Getters and setters
    private Product product;
    private int quantity;

    /**
     * Creates a new cart item
     * @param product the product being added to cart
     * @param quantity the quantity of the product
     */

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}