package org.unicesumar.paymentMethod;

import org.unicesumar.enums.PaymentEnum;
import org.unicesumar.strategy.PaymentStrategy;

public class BankSlipPayment implements PaymentStrategy {

    private PaymentEnum paymentEnum = PaymentEnum.BankSlip;

    public BankSlipPayment() {}

    @Override
    public void pay() {
        System.out.println("Bank Slip Payment Method");
    }

    @Override
    public PaymentEnum getEnumType() {
        return paymentEnum;
    }
}
