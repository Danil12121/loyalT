package com.loyalt.bankkernel;

public record PaymentEvent(
        Long transactionId,
        String partnerId,
        int clientId,
        Long amount
) {}