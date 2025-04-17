package org.unicesumar.paymentMethod;

import org.unicesumar.strategy.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {

    public CreditCardPayment() {}

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " to the credit card");
    }
}
