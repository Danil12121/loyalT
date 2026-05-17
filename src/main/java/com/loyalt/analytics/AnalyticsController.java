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

    /**
     * ПРИЕМ СЫРЫХ ДАННЫХ: Регистрация новой транзакции/события в ClickHouse
     */
    @PostMapping
    public ResponseEntity<Analytics> create(@org.springframework.lang.NonNull @RequestBody Analytics analyticsRecord) {
        log.info("REST request to record new ClickHouse transaction: {}", analyticsRecord);
        Analytics created = service.create(analyticsRecord);
        return ResponseEntity.ok(created);
    }

    /**
     * ПОЛУЧЕНИЕ СТАТИСТИКИ: Агрегированная аналитика из ClickHouse (работает молниеносно)
     */
    @GetMapping("/{partnerId}/stats")
    public ResponseEntity<AnalyticsDTO> getPartnerStats(
            @PathVariable String partnerId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        
        log.info("REST request to get ClickHouse stats for partner: {} from {} to {}", partnerId, from, to);
        AnalyticsDTO stats = service.getPartnerStatistics(partnerId, from, to);
        return ResponseEntity.ok(stats);
    }

    /**
     * СЛУЖЕБНЫЙ: Получить список транзакций (Внимание: добавлен LIMIT для безопасности ClickHouse)
     */
    @GetMapping("/transactions")
    public List<Analytics> getAllTransactions(@RequestParam(value = "limit", defaultValue = "1000") int limit) {
        log.info("REST request to get raw transactions with limit {}", limit);
        return service.getAllTransactions(limit);
    }

    /**
     * СЛУЖЕБНЫЙ: Получить конкретную транзакцию по ее ID
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Analytics> getTransactionById(@PathVariable Long id) {
        log.info("REST request to get raw transaction by id: {}", id);
        Optional<Analytics> transaction = service.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * УДАЛЕНИЕ: Удаление в ClickHouse через Lightweight Delete / Mutation
     */
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
}