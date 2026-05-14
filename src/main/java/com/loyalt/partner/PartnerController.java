package com.loyalt.partner;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/partners") // Базовый URL
public class PartnerController {

    private final PartnerService service;

    public PartnerController(PartnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Partner> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Partner create(@RequestBody Partner partner) {
        return service.create(partner);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, 
                         @RequestParam String type, 
                         @RequestParam Double value) {
        service.update(id, type, value);
        return "Updated successfully";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}