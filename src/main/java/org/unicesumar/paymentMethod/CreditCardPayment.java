package org.unicesumar.paymentMethod;

import org.unicesumar.enums.PaymentEnum;
import org.unicesumar.strategy.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {

    private PaymentEnum paymentEnum = PaymentEnum.CreditCard;

    public CreditCardPayment() {}

    @Override
    public void pay() {
        System.out.println("Paying to the credit card");
    }

    @Override
    public PaymentEnum getEnumType() {
        return paymentEnum;
    }
}
