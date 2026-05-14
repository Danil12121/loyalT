package com.loyalt.analytics;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class AnalyticsService {

    private final AnalyticsRepository repository;

    public AnalyticsService(AnalyticsRepository repository) {
        this.repository = repository;
    }

    public List<Analytics> getAll() {
        return repository.findAll();
    }

    public Optional<Analytics> getOne(String id) {
        return repository.findByPartnerId(id);
    }

    public Analytics create(@org.springframework.lang.NonNull Analytics partner) {
        return repository.save(partner);
    }

    public void update(String id, int totalClients, int newClients, Long totalTransactions, LocalDateTime date) {
        repository.updateValue(id, totalClients, newClients, totalTransactions, date);
    }

    public void delete(String id) {
        repository.deleteByPartnerId(id);
    }
}