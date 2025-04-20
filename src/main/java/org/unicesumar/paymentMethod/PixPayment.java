package org.unicesumar.paymentMethod;

import org.unicesumar.enums.PaymentEnum;
import org.unicesumar.strategy.PaymentStrategy;

import java.util.UUID;

public class PixPayment implements PaymentStrategy {

    private PaymentEnum paymentEnum = PaymentEnum.Pix;

    public PixPayment() {}

    @Override
    public void pay() {
        System.out.println("Pix payment completed successfully! Authentication key: " + UUID.randomUUID());
    }

    @Override
    public PaymentEnum getEnumType() {
        return paymentEnum;
    }
}
