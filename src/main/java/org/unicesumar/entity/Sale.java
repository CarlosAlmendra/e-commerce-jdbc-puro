package org.unicesumar.entity;

import org.unicesumar.enums.PaymentEnum;

import java.util.UUID;

public class Sale extends Entity {
    private User user;
    private Cart cart;
    private double totalValue;
    private PaymentEnum paymentEnum;

    public Sale(User user, Cart cart, double totalValue, PaymentEnum paymentEnum) {
        this.user = user;
        this.cart = cart;
        this.totalValue = totalValue;
        this.paymentEnum = paymentEnum;
    }

    public Sale(UUID uuid, User user, Cart cart, double totalValue, PaymentEnum paymentEnum) {
        super(uuid);
        this.user = user;
        this.cart = cart;
        this.totalValue = totalValue;
        this.paymentEnum = paymentEnum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public PaymentEnum getPaymentEnum() {
        return paymentEnum;
    }

    public void setPaymentEnum(PaymentEnum paymentEnum) {
        this.paymentEnum = paymentEnum;
    }

}
