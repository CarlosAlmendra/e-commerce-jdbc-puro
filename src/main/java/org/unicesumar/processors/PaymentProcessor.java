package org.unicesumar.processors;

import org.unicesumar.enums.PaymentEnum;
import org.unicesumar.paymentMethod.BankSlipPayment;
import org.unicesumar.paymentMethod.CreditCardPayment;
import org.unicesumar.paymentMethod.PixPayment;
import org.unicesumar.strategy.PaymentStrategy;

import java.util.Map;

public class PaymentProcessor {

    Map<Integer, PaymentStrategy> paymentStrategyMap = Map.of(
            1, new BankSlipPayment(),
            2, new CreditCardPayment(),
            3, new PixPayment()
    );

    public PaymentProcessor() {}

    public void selectPaymentStrategy(Integer option) {
        PaymentStrategy strategy = paymentStrategyMap.get(option);

        if (strategy == null) {
            System.out.println("\nInvalid option. Please select a valid payment method.");
            return;
        }

        strategy.pay();
    }

    public PaymentEnum getPaymentStrategyEnum(Integer option) {
        return paymentStrategyMap.get(option).getEnumType();
    }
}
