package org.unicesumar.paymentMethod;

import org.unicesumar.strategy.PaymentStrategy;

public class PixPayment implements PaymentStrategy {

    public PixPayment() {}

    @Override
    public void pay(double amount) {
        System.out.println("PixPayment.pay(" + amount + ")");
    }
}
