package com.loyalt.bankkernel;


import com.loyalt.analytics.AnalyticProducer;
import com.loyalt.analytics.AnalyticsEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PaymentConsumer {

    private final BankService paymentService;
    private final AnalyticProducer analyticProducer;

    public PaymentConsumer(BankService paymentService, AnalyticProducer analyticProducer) {
        this.paymentService = paymentService;
        this.analyticProducer = analyticProducer;
    }

    @KafkaListener(topics = "bank.payments", groupId = "loyalty-main-group")
    public void consumePayment(PaymentEvent event) {
        System.out.println("Банк: получена оплата от клиента " + event.clientId());

        try {

            AnalyticsEvent analyticsEvent = new AnalyticsEvent(
                    event.transactionId(),
                    event.partnerId(),
                    event.clientId(),
                    event.amount(),
                    LocalDateTime.now()
            );
            analyticProducer.sendToAnalytics(analyticsEvent);

        } catch (Exception e) {
            System.err.println("Ошибка обработки платежа: " + e.getMessage());
        }
    }
}
