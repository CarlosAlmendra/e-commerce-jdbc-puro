package org.unicesumar;

import org.unicesumar.entity.*;
import org.unicesumar.processors.PaymentProcessor;
import org.unicesumar.repository.*;
import org.unicesumar.util.DatabaseConnection;
import org.unicesumar.util.DatabaseInitializer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.util.Objects.isNull;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            Connection conn = DatabaseConnection.getConnection();

            ProductRepository productRepo = new ProductRepository(conn);
            UserRepository userRepo = new UserRepository(conn);
            CartItemsRepository cartItemsRepository = new CartItemsRepository(conn);
            SaleRepository saleRepo = new SaleRepository(conn);
            CartRepository cartRepo = new CartRepository(conn);

            boolean running = true;

            Product product = new Product(UUID.randomUUID(), "Hanes Men's Originals T-Shirt", 10);
            productRepo.save(product);

            while (running) {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1 - Search User");
                System.out.println("2 - Search Products");
                System.out.println("3 - Register User");
                System.out.println("4 - Make a Sale");
                System.out.println("0 - Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        searchUser(userRepo);
                        break;
                    case 2:
                        searchProducts(productRepo);
                        break;
                    case 3:
                        registerUser(userRepo);
                        break;
                    case 4:
                        makeSale(productRepo, userRepo, cartItemsRepository,
                                cartRepo, saleRepo);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            System.out.println("Exiting system. Goodbye!");
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

    private static void searchProducts(ProductRepository productRepo) {
        List<Product> products = productRepo.findAll();

        System.out.println("\n=== Available Products ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - $%.2f\n", i + 1, p.getName(), p.getPrice());
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
        List<Product> selectedProduct = new ArrayList<>();
        User saleUser;
        Double totalValue = 0.0;

        System.out.println("\nEnter user email:");
        String email = scanner.next();

        if(isNull(userRepo.findByEmail(email))) {
            System.out.println("User not finded");
            return;
        } else
            saleUser = userRepo.findByEmail(email);

        Cart cart = new Cart(saleUser);
        cartRepo.save(cart);

        System.out.println("=== User finded ===\nName: " +
                saleUser.getName() + "\nEmail: " +
                saleUser.getEmail() + "\n");

        System.out.println("\n=== Products for Sale ===");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - $%.2f\n", i + 1, p.getName(), p.getPrice());
        }

        while (true) {
            System.out.print("\nEnter product number to add to cart (0 to finish): ");
            int productNumber = scanner.nextInt();
            if (productNumber == 0) break;
            System.out.print("\nEnter product quantity: ");
            int productQuantity = scanner.nextInt();

            if (productNumber < 1 || productNumber > products.size()) {
                System.out.println("Invalid product number.");
                continue;
            }

            selectedProduct.add(products.get(productNumber - 1));
            for(Product product : selectedProduct) {
                cartItemsRepo.save(new CartItems(saleUser, product, cart, productQuantity, product.getPrice()));
                totalValue += getTotalValue(product, productQuantity);
            }
        }

        System.out.print("\nEnter the payment method: ");
        System.out.print("\n1- Bank Slip  ");
        System.out.print("\n2- Credit Card");
        System.out.print("\n3- PIX\n");
        System.out.print("\nType option: \n");

        int paymentMethod = scanner.nextInt();
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        Sale sale = new Sale(saleUser, cart, totalValue, paymentProcessor.getPaymentStrategyEnum(paymentMethod));
        saleRepo.save(sale);

        System.out.println("======Sale data=======");
        System.out.println("User name: " + sale.getUser().getName());
        System.out.println("User email: " + sale.getUser().getEmail());
        System.out.println("Products: ");
        List<CartItems> cartItems = cartItemsRepo.findAllProductsByCart(sale.getCart().getUuid());
        for(CartItems cartItem : cartItems) {
            System.out.println("Name: " + cartItem.getProduct().getName() + " - Price: " + cartItem.getProduct().getPrice() + " - Quantity: " + cartItem.getQuantity());
        }
        System.out.println("Total value: " + sale.getTotalValue());
        paymentProcessor.selectPaymentStrategy(paymentMethod);

        cartItems = null;
        System.out.println("Purchase completed successfully!");
    }

    public static Double getTotalValue(Product product, int quantity) {
        return product.getPrice() * quantity;
    }
}
