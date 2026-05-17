package com.loyalt.bankkernel;

import java.util.List;
import java.util.Optional;

import com.loyalt.partner.Partner;
import com.loyalt.partner.PartnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    private final PaymentRepository repository;
    private final PartnerService partnerService;

    public BankService(PaymentRepository paymentRepository, PartnerService partnerService) {
        this.repository = paymentRepository;
        this.partnerService = partnerService;
    }

    @Transactional
    public void processPayment(PaymentEvent event) {
        Partner rule = partnerService.getRule(event.partnerId());

        if (rule == null) {
            System.out.println("У партнера " + event.partnerId() + " нет программы лояльности. Пропускаем.");
            return;
        }


        Payment balance = repository.findByClientIdAndPartnerIdAndLoyaltyType(
                event.clientId(),
                event.partnerId(),
                rule.getType()
        ).orElse(new Payment(
                event.partnerId(),
                0.0,
                rule.getType(),
                0,
                rule.getValue().intValue()
        ));

        balance.setClientId(event.clientId());

        if ("CASHBACK".equals(rule.getType())) {
            double points = event.amount() * (rule.getValue() / 100);
            balance.setBalance(balance.getBalance() + points);
            System.out.println(" Начислен кэшбэк: " + points);

        } else if ("STAMP_CARD".equals(rule.getType())) {
            balance.setCurrValue(balance.getCurrValue() + 1);

            if (balance.getCurrValue() >= rule.getValue()) {
                balance.setBalance(balance.getBalance() + 1);
                balance.setCurrValue(0);
                System.out.println("Выдан бесплатный товар по карточке!");
            }
        }

        repository.save(balance);
    }


    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Optional<Payment> getOne(int clientId, String partnerId, String type) {
        return repository.findByClientIdAndPartnerIdAndLoyaltyType(clientId, partnerId, type);
    }

    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    public void delete(String partnerId, int clientId) {
        repository.delete(partnerId, clientId);
    }
}