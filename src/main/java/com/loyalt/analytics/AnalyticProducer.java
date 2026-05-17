package com.loyalt.analytics;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalyticProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AnalyticProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToAnalytics(AnalyticsEvent event) {
        kafkaTemplate.send("loyalty.analytics", event);
        System.out.println(" Отправлено в аналитику: " + event.transactionId());
    }
}
