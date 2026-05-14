package com.loyalt.analytics;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics") // Базовый URL
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Analytics> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Analytics create(@org.springframework.lang.NonNull@RequestBody Analytics analytics) {
        return service.create(analytics);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String partnerId, 
                @RequestParam int totalClients, 
                @RequestParam int newClients, 
                @RequestParam Long totalTransactions, 
                @RequestParam LocalDateTime date) {
        service.update(partnerId, totalClients, newClients, totalTransactions, date);
        return "Updated successfully";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String name) {
        service.delete(name);
    }
}