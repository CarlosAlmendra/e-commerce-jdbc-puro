package org.unicesumar.strategy;

import org.unicesumar.enums.PaymentEnum;

public interface PaymentStrategy {
    void pay();
    PaymentEnum getEnumType();
}
