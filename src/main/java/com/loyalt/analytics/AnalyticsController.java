package com.loyalt.analytics;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsController.class);
    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Analytics> create(@org.springframework.lang.NonNull @RequestBody Analytics analyticsRecord) {
        log.info("REST request to record new ClickHouse transaction: {}", analyticsRecord);
        Analytics created = service.create(analyticsRecord);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/{partnerId}/stats")
    public ResponseEntity<AnalyticsDTO> getPartnerStats(
            @PathVariable String partnerId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        
        log.info("REST request to get ClickHouse stats for partner: {} from {} to {}", partnerId, from, to);
        AnalyticsDTO stats = service.getPartnerStatistics(partnerId, from, to);
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/transactions")
    public List<Analytics> getAllTransactions(@RequestParam(value = "limit", defaultValue = "1000") int limit) {
        log.info("REST request to get raw transactions with limit {}", limit);
        return service.getAllTransactions(limit);
    }


    @GetMapping("/transactions/{id}")
    public ResponseEntity<Analytics> getTransactionById(@PathVariable Long id) {
        log.info("REST request to get raw transaction by id: {}", id);
        Optional<Analytics> transaction = service.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.info("REST request to delete transaction with id from ClickHouse: {}", id);
        try {
            service.deleteTransaction(id);
            log.info("Transaction {} deletion query sent to ClickHouse", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete transaction {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("REST request to delete partner with id: {}", id);

        if (id == null) {
            log.warn("Delete attempt with null or empty id");
            return;
        }

        try {
            service.deleteTransaction(id);
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

