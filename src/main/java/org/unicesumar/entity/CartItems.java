package org.unicesumar.entity;

import java.util.UUID;

public class CartItems extends Entity {

    private User user;
    private Product product;
    private Cart cart;
    private int quantity;
    private double price;

    public CartItems(User user, Product product, Cart cart, int quantity, double price) {
        super(UUID.randomUUID());
        this.user = user;
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItems(UUID uuid, User user, Product product, Cart cart, int quantity, double price) {
        super(uuid);
        this.user = user;
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.price = price;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
