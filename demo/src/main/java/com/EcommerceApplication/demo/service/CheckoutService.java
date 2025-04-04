// src/main/java/com/example/ecommerce/service/CheckoutService.java
package com.EcommerceApplication.demo.service;

import com.EcommerceApplication.demo.exception.EmptyCartException;
import com.EcommerceApplication.demo.exception.*;
import com.EcommerceApplication.demo.model.product.Product;
import com.EcommerceApplication.demo.model.shipping.Shippable;
import com.EcommerceApplication.demo.model.user.Customer;
import com.EcommerceApplication.demo.model.cart.Cart;
import com.EcommerceApplication.demo.model.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {
    private static final double SHIPPING_FEE_PER_KG = 30.0;
    private final ShippingService shippingService;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        validateCheckout(customer, cart);

        double subtotal = calculateSubtotal(cart);
        List<Shippable> shippableItems = new ArrayList<>();
        double shippingFee = calculateShippingFee(cart, shippableItems);
        double totalAmount = subtotal + shippingFee;

        validateCustomerBalance(customer, totalAmount);
        processShipping(shippableItems);
        printReceipt(cart, subtotal, shippingFee, totalAmount);
        completeTransaction(customer, cart, totalAmount);
    }

    private void validateCheckout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cannot checkout with an empty cart");
        }

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            if (product.getQuantity() < item.getQuantity()) {
                throw new OutOfStockException(
                        String.format("Product %s only has %d items in stock",
                                product.getName(), product.getQuantity()));
            }

            if (product.isExpirable() && product.isExpired()) {
                throw new ProductExpiredException(
                        String.format("Product %s has expired", product.getName()));
            }
        }
    }

    private double calculateSubtotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    private double calculateShippingFee(Cart cart, List<Shippable> shippableItems) {
        double shippingWeight = 0;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().isShippable()) {
                shippableItems.add((Shippable) item.getProduct());
                shippingWeight += item.getProduct().getWeight() * item.getQuantity();
            }
        }

        return shippingWeight * SHIPPING_FEE_PER_KG;
    }

    private void validateCustomerBalance(Customer customer, double totalAmount) {
        if (customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Required: %.2f, Available: %.2f",
                            totalAmount, customer.getBalance()));
        }
    }

    private void processShipping(List<Shippable> shippableItems) {
        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }
    }

    private void printReceipt(Cart cart, double subtotal, double shippingFee, double totalAmount) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.2f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.2f%n", subtotal);
        System.out.printf("Shipping %.2f%n", shippingFee);
        System.out.printf("Amount %.2f%n", totalAmount);
    }

    private void completeTransaction(Customer customer, Cart cart, double totalAmount) {
        customer.deductBalance(totalAmount);
        System.out.printf("Customer current balance after payment: %.2f%n", customer.getBalance());

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }

        cart.clear();
    }
}