package com.loyalt.analytics;

import java.time.LocalDateTime;

public record AnalyticsEvent(
        Long transactionId,
        String partnerId,
        int clientId,
        Long amount,
        LocalDateTime timestamp
) {}