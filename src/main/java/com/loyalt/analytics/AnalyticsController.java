package com.loyalt.analytics;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/analytics") // Базовый URL
public class AnalyticsController {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsController.class);
    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Analytics> getAll() {
        log.info("REST request to get all partners analytics");
        List<Analytics> partnersAnalytics = service.getAll();
        log.info("Retrieved {} partners", partnersAnalytics != null ? partnersAnalytics.size() : 0);
        return partnersAnalytics;
    }

    @PostMapping
    public Analytics create(@org.springframework.lang.NonNull@RequestBody Analytics partnerAnalytics) {
        log.info("REST request to create partner analytics: {}", partnerAnalytics);

        Analytics created = service.create(partnerAnalytics);
        log.info("Partner analytics created successfully");
        return created;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, 
                @RequestBody UpdateAnalyticRequest updateAnalyticRequest) {
        
        log.info("REST request to update partner analytics - id: {}, body: {}", id, updateAnalyticRequest);

        if (id == null || id.trim().isEmpty()) {
            log.error("Update failed: partner id is null or empty");
            return "Update failed: invalid id";
        }

        try {
            service.update(id, updateAnalyticRequest.getTotalClients(), 
                            updateAnalyticRequest.getNewClients(), 
                            updateAnalyticRequest.getTotalTransactions(), 
                            updateAnalyticRequest.getDate());

            log.info("Partner {} analytics updated successfully (total clients: {}, new clients: {}, total tranactions: {}, date: {})",
                    id, updateAnalyticRequest.getTotalClients(), 
                            updateAnalyticRequest.getNewClients(), 
                            updateAnalyticRequest.getTotalTransactions(), 
                            updateAnalyticRequest.getDate());
            return "Updated successfully";
        } catch (Exception e) {
            log.error("Failed to update partner {}: {}", id, e.getMessage(), e);
            return "Update failed: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("REST request to delete partner with id: {}", id);

        if (id == null || id.trim().isEmpty()) {
            log.warn("Delete attempt with null or empty id");
            return;
        }

        try {
            service.delete(id);
            log.info("Partner {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Failed to delete partner {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}

class UpdateAnalyticRequest {

    private String partnerId;
    private int totalClients;
    private Long totalTransactions;
    private int newClients;
    private LocalDateTime date;

    public UpdateAnalyticRequest() {
    }

    public String getPartnerId() {
        return partnerId;
    }

    public int getTotalClients() {
        return totalClients;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public int getNewClients() {
        return newClients;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public void setNewClients(int newClients) {
        this.newClients = newClients;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UpdateAnalyticRequest{" +
                "partnerId='" + partnerId + '\'' +
                ", totalClients=" + totalClients + '\'' +
                ", totalTransactions=" + totalTransactions + '\'' +
                ", newClients=" + newClients + '\'' +
                ", date=" + date +
                '}';
    }
}
