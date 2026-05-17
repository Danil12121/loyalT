package com.loyalt.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic paymentsTopic() {
        return TopicBuilder.name("bank.payments").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic analyticsTopic() {
        return TopicBuilder.name("loyalty.analytics").partitions(1).replicas(1).build();
    }
}