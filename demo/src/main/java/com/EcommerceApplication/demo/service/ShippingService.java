// src/main/java/com/example/ecommerce/service/ShippingService.java
package com.EcommerceApplication.demo.service;

import com.EcommerceApplication.demo.model.shipping.Shippable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {
    public void processShipment(List<Shippable> shippableItems) {
        if (shippableItems == null || shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        double totalWeight = 0;

        for (Shippable item : shippableItems) {
            System.out.printf("%dx %s %.0fg%n", 1, item.getName(), item.getWeight() * 1000);
            totalWeight += item.getWeight();
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}