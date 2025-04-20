package org.unicesumar;

import org.unicesumar.entity.*;
import org.unicesumar.processors.PaymentProcessor;
import org.unicesumar.repository.*;
import org.unicesumar.util.DatabaseConnection;
import org.unicesumar.util.DatabaseInitializer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            Connection conn = DatabaseConnection.getConnection();

            ProductRepository productRepo = new ProductRepository(conn);
            UserRepository userRepo = new UserRepository(conn);
            CartItemsRepository cartItemsRepo = new CartItemsRepository(conn);
            SaleRepository saleRepo = new SaleRepository(conn);
            CartRepository cartRepo = new CartRepository(conn);

            addDefaultProducts(productRepo);

            boolean running = true;
            while (running) {
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("1 - Search User");
                System.out.println("2 - View Products");
                System.out.println("3 - Register User");
                System.out.println("4 - Make a Sale");
                System.out.println("0 - Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> searchUser(userRepo);
                    case 2 -> viewProducts(productRepo);
                    case 3 -> registerUser(userRepo);
                    case 4 -> makeSale(productRepo, userRepo, cartItemsRepo, cartRepo, saleRepo);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }

            System.out.println("Exiting the system. Goodbye!");
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void searchUser(UserRepository userRepo) {
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();
        User user = userRepo.findByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getName());
        } else {
            System.out.println("User not found.");
        }
    }

    private static void viewProducts(ProductRepository productRepo) {
        List<Product> products = productRepo.findAll();
        System.out.println("\n=== AVAILABLE PRODUCTS ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - $%.2f%n", i + 1, p.getName(), p.getPrice());
        }
    }

    private static void registerUser(UserRepository userRepo) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();

        System.out.print("Enter user email: ");
        String email = scanner.nextLine();

        if (userRepo.findByEmail(email) != null) {
            System.out.println("A user with this email already exists.");
            return;
        }

        User user = new User(name, email);
        userRepo.save(user);

        System.out.println("User registered successfully!");
    }

    private static void makeSale(ProductRepository productRepo, UserRepository userRepo,
                                 CartItemsRepository cartItemsRepo, CartRepository cartRepo, SaleRepository saleRepo) {
        List<Product> products = productRepo.findAll();
        List<Product> selectedProducts = new ArrayList<>();
        double totalValue = 0.0;

        System.out.print("Enter user email: ");
        String email = scanner.next();

        User saleUser = userRepo.findByEmail(email);
        if (isNull(saleUser)) {
            System.out.println("User not found.");
            return;
        }

        Cart cart = new Cart(saleUser);
        cartRepo.save(cart);

        System.out.printf("User found: %s (%s)%n", saleUser.getName(), saleUser.getEmail());

        System.out.println("\n=== PRODUCTS FOR SALE ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - $%.2f%n", i + 1, p.getName(), p.getPrice());
        }

        while (true) {
            System.out.print("\nEnter product number to add to cart (0 to finish): ");
            int productNumber = scanner.nextInt();
            if (productNumber == 0) break;

            if (productNumber < 1 || productNumber > products.size()) {
                System.out.println("Invalid product number.");
                continue;
            }

            Product selected = products.get(productNumber - 1);
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            cartItemsRepo.save(new CartItems(saleUser, selected, cart, quantity, selected.getPrice()));
            selectedProducts.add(selected);
            totalValue += calculateTotal(selected, quantity);
        }

        System.out.println("\n=== PAYMENT METHODS ===");
        System.out.println("1 - Bank Slip");
        System.out.println("2 - Credit Card");
        System.out.println("3 - PIX");
        System.out.print("Choose a payment method: ");
        int paymentMethod = scanner.nextInt();

        PaymentProcessor processor = new PaymentProcessor();
        Sale sale = new Sale(saleUser, cart, totalValue, processor.getPaymentStrategyEnum(paymentMethod));
        saleRepo.save(sale);

        System.out.println("\n====== SALE SUMMARY ======");
        System.out.printf("Customer: %s (%s)%n", saleUser.getName(), saleUser.getEmail());
        System.out.println("Products purchased:");
        List<CartItems> cartItems = cartItemsRepo.findAllProductsByCart(cart.getUuid());
        for (CartItems item : cartItems) {
            System.out.printf("• %s - $%.2f x%d%n", item.getProduct().getName(),
                    item.getProduct().getPrice(), item.getQuantity());
        }
        System.out.printf("Total: $%.2f%n", sale.getTotalValue());
        processor.selectPaymentStrategy(paymentMethod);

        System.out.println("Purchase completed successfully!");
    }

    private static void addDefaultProducts(ProductRepository productRepo) {
        if (!productRepo.findAll().isEmpty()) return;

        List<Product> defaultProducts = Arrays.asList(
                new Product("Lenovo Ideapad Notebook", 3499.90),
                new Product("Samsung Galaxy S21 Smartphone", 2999.99),
                new Product("RedDragon Mechanical Keyboard", 249.90),
                new Product("Logitech G203 Mouse", 149.50),
                new Product("LG 24'' Full HD Monitor", 899.00)
        );

        defaultProducts.forEach(productRepo::save);
    }

    private static double calculateTotal(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
}
