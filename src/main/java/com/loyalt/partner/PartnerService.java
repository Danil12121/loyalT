package com.loyalt.partner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class PartnerService {

    private final PartnerRepository repository;

    public PartnerService(PartnerRepository repository) {
        this.repository = repository;
    }

    public List<Partner> getAll() {
        return repository.findAll();
    }

    public Optional<Partner> getOne(String id, String type) {
        return repository.findByIdAndType(id, type);
    }

    public Partner create(@org.springframework.lang.NonNull Partner partner) {
        return repository.save(partner);
    }

    @Transactional
    public void update(String id, String type, Double value) {
        repository.updateValue(id, type, value);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}