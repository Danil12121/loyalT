package com.loyalt.bankkernel;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class BankService {

    private final PaymentRepository repository;

    public BankService(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Optional<Payment> getOne(Long clientId, String partnerId, String type) {
        return repository.findByClientIdAndPartnerIdAndLoyalType(clientId, partnerId, type);
    }

    public Payment create(@org.springframework.lang.NonNull Payment partner) {
        return repository.save(partner);
    }

    @Transactional

    public void update(Long clientId, String partnerId, String type, Double value, int currValue, int maxValueorPercent) {
        repository.update(clientId, partnerId, type, value, currValue, maxValueorPercent);
    }

    public void deletePayment(String partnerId, Long clientId) {
        repository.delete(partnerId, clientId);
    }
}