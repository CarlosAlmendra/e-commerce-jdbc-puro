package org.unicesumar.paymentMethod;

import org.unicesumar.strategy.PaymentStrategy;

public class BankSlipPayment implements PaymentStrategy {

    public BankSlipPayment() {}

    @Override
    public void pay(double amount) {
        System.out.println("Bank Slip Payment Method");
    }
}
