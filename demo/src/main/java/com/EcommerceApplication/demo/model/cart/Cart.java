// src/main/java/com/example/ecommerce/model/Cart.java
package com.EcommerceApplication.demo.model.cart;

import com.EcommerceApplication.demo.model.product.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart containing items for purchase
 */
@Getter
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    /**
     * Adds a product to the cart with specified quantity
     * @param product the product to add
     * @param quantity the quantity to add
     * @throws IllegalArgumentException if quantity is invalid or exceeds available stock
     */

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock");
        }

        items.add(new CartItem(product, quantity));
    }
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Removes all items from the cart
     */

    public void clear() {
        items.clear();
    }
    /**
     * Checks if the cart is empty
     * @return true if cart has no items, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
}