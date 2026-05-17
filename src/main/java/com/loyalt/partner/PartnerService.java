package com.loyalt.partner;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {

    private final PartnerRepository repository;

    public PartnerService(PartnerRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "partnerRules", key = "#partnerId")
    public Partner getRule(String partnerId) {
        System.out.println("🗄Чтение из БД (Postgres) для партнера: " + partnerId);
        return repository.findById(partnerId).orElse(null);
    }

    @CachePut(value = "partnerRules", key = "#partner.id")
    public Partner save(Partner partner) {
        return repository.save(partner);
    }

    @CacheEvict(value = "partnerRules", key = "#id")
    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Partner> getAll() {
        return repository.findAll();
    }

    public Optional<Partner> getOne(String id, String type) {
        return repository.findByIdAndType(id, type);
    }
}