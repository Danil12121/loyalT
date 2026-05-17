package com.loyalt.analytics;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AnalyticConsumer {

    private final AnalyticsRepository analyticsRepository;
    private final AtomicLong transactionIdGenerator = new AtomicLong(System.currentTimeMillis());

    public AnalyticConsumer(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @KafkaListener(topics = "loyalty.analytics", groupId = "analytics-group")
    public void consumeAnalytics(AnalyticsEvent event) {
        System.out.println(" Аналитика: обновляю статистику для партнера " + event.partnerId());

        Analytics analytics = new Analytics(
                transactionIdGenerator.incrementAndGet(),
                event.partnerId(),
                event.clientId(),
                event.amount(),
                LocalDateTime.now()
        );

        analyticsRepository.save(analytics);

        System.out.println(" Аналитика обновлена!");
    }
}