package com.EcommerceApplication.demo;

import com.EcommerceApplication.demo.exception.InsufficientBalanceException;
import com.EcommerceApplication.demo.model.cart.Cart;
import com.EcommerceApplication.demo.model.product.Product;
import com.EcommerceApplication.demo.model.user.Customer;
import com.EcommerceApplication.demo.service.CheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * Main entry point for the E-Commerce application with demo scenarios
 */
@SpringBootApplication
public class EcommerceApplication {
	private static final Logger logger = LoggerFactory.getLogger(EcommerceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CheckoutService checkoutService) {
		return args -> {
			try {
				runDemoScenarios(checkoutService);
			} catch (Exception e) {
				logger.error("Failed to run demo scenarios", e);
				throw e;
			}
		};
	}

	private void runDemoScenarios(CheckoutService checkoutService) {
		logger.info("\n=== Starting E-Commerce System Demo ===\n");

		DemoData data = createDemoData();

		runSuccessfulCheckout(checkoutService, data);
		runInsufficientBalanceScenario(checkoutService, data);

		logger.info("=== Demo completed successfully ===");
	}

	private DemoData createDemoData() {
		return new DemoData(
				new Product("prod-1", "Cheese", 100.0, 10)
						.makeExpirable(LocalDate.now().plusDays(10))
						.makeShippable(0.4),
				new Product("prod-2", "Biscuits", 150.0, 5)
						.makeExpirable(LocalDate.now().plusDays(30))
						.makeShippable(0.7),
				new Product("prod-3", "TV", 500.0, 3)
						.makeShippable(10.0),
				new Product("prod-4", "Scratch Card", 50.0, 100),
				new Customer("cust-1", "John Doe", 1000.0),
				new Customer("cust-2", "Jane Smith", 100.0)
		);
	}

	private void runSuccessfulCheckout(CheckoutService checkoutService, DemoData data) {
		logger.info("=== Running successful checkout scenario ===");

		Cart cart = new Cart();
		cart.addItem(data.cheese(), 2);
		cart.addItem(data.biscuits(), 1);
		cart.addItem(data.scratchCard(), 1);

		checkoutService.checkout(data.primaryCustomer(), cart);
	}

	private void runInsufficientBalanceScenario(CheckoutService checkoutService, DemoData data) {
		logger.info("=== Testing insufficient balance scenario ===");

		Cart expensiveCart = new Cart();
		expensiveCart.addItem(data.tv(), 1);

		try {
			checkoutService.checkout(data.secondaryCustomer(), expensiveCart);
		} catch (InsufficientBalanceException e) {
			logger.warn("Expected exception: {}", e.getMessage());
		}
	}

	private record DemoData(
			Product cheese,
			Product biscuits,
			Product tv,
			Product scratchCard,
			Customer primaryCustomer,
			Customer secondaryCustomer
	) {}
}