package com.loyalt.bankkernel;

public record PaymentEvent(
        String transactionId,
        String partnerId,
        int clientId,
        double amount
) {}