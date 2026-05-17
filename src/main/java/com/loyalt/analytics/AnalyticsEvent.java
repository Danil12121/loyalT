package com.loyalt.analytics;

import java.time.LocalDateTime;

public record AnalyticsEvent(
        String transactionId,
        String partnerId,
        int clientId,
        double amount,
        LocalDateTime timestamp
) {}