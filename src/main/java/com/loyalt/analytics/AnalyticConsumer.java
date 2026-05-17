package com.loyalt.analytics;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AnalyticConsumer {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticConsumer(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @KafkaListener(topics = "loyalty.analytics", groupId = "analytics-group")
    public void consumeAnalytics(AnalyticsEvent event) {
        System.out.println(" Аналитика: обновляю статистику для партнера " + event.partnerId());

        Analytics stats = analyticsRepository.findByPartnerId(event.partnerId())
                .orElse(new Analytics(event.partnerId(), 0, 0L, 0, LocalDateTime.now()));

        stats.setTotalTransactions(stats.getTotalTransactions() + 1);

        stats.setTotalClients(stats.getTotalClients() + 1);
        stats.setDate(LocalDateTime.now());

        analyticsRepository.save(stats);

        System.out.println("Аналитика обновлена!");
    }
}