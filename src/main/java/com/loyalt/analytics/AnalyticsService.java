package com.loyalt.analytics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnalyticsService {

    private final JdbcTemplate clickhouseJdbcTemplate;

    public AnalyticsService(@Qualifier("clickhouseJdbcTemplate") JdbcTemplate clickhouseJdbcTemplate) {
        this.clickhouseJdbcTemplate = clickhouseJdbcTemplate;
    }

    public Analytics create(Analytics record) {
        // Если ID транзакции не пришел, генерируем его вручную (так как ClickHouse не умеет AUTO_INCREMENT)
        if (record.getTransactionId() == null) {
            record.setTransactionId(ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE));
        }
        if (record.getDate() == null) {
            record.setDate(LocalDateTime.now());
        }

        String sql = "INSERT INTO transactions (transaction_id, partner_id, client_id, amount, timestamp) VALUES (?, ?, ?, ?, ?)";
        clickhouseJdbcTemplate.update(sql,
                record.getTransactionId(),
                record.getPartnerId(),
                record.getClientId(),
                record.getAmount(),
                record.getDate()
        );
        return record;
    }

    public AnalyticsDTO getPartnerStatistics(String partnerId, LocalDateTime from, LocalDateTime to) {
    
    String sql = 
        "SELECT " +
        "    count() as total_trans, " + // Всего транзакций за период
        "    count(DISTINCT client_id) as total_users, " + // Уникальных пользователей за период
        "    ( " +
        "        SELECT count(DISTINCT client_id) " +
        "        FROM transactions " +
        "        WHERE partner_id = ? " +
        "        GROUP BY partner_id " +
        "        HAVING min(timestamp) >= ? AND min(timestamp) <= ? " +
        "    ) as new_users " + // Пользователи, которые впервые появились в этот период
        "FROM transactions " +
        "WHERE partner_id = ? AND timestamp >= ? AND timestamp <= ?";

    try {
        return clickhouseJdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            long totalTransactions = rs.getLong("total_trans");
            long totalUsers = rs.getLong("total_users");
            long newUsers = rs.getLong("new_users"); // Если подзапрос вернет null, JdbcTemplate вернет 0

            // Контроллер ожидает объект AnalyticsDTO. 
            // Поле date в DTO обычно заполняют текущим временем формирования отчета или датой 'to'
            return new AnalyticsDTO(
                    partnerId,
                    LocalDateTime.now(), 
                    totalUsers,
                    totalTransactions,
                    newUsers
            );
        }, 
        partnerId, Timestamp.valueOf(from), Timestamp.valueOf(to), // Параметры для подзапроса new_users
        partnerId, Timestamp.valueOf(from), Timestamp.valueOf(to)  // Параметры для основного запроса
        );
        
    } catch (Exception e) {
        // На случай, если за период вообще не было данных, возвращаем пустой DTO, а не падаем по ошибке
        return new AnalyticsDTO(partnerId, LocalDateTime.now(), 0, 0, 0);
    }
}

    public List<Analytics> getAllTransactions(int limit) {
        String sql = "SELECT transaction_id, partner_id, client_id, amount, timestamp FROM transactions LIMIT ?";
        return clickhouseJdbcTemplate.query(sql, (rs, rowNum) -> new Analytics(
                rs.getLong("transaction_id"),
                rs.getString("partner_id"),
                rs.getLong("client_id"),
                rs.getLong("amount"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        ), limit);
    }

    public Optional<Analytics> getTransactionById(Long id) {
        String sql = "SELECT transaction_id, partner_id, client_id, amount, timestamp FROM transactions WHERE transaction_id = ? LIMIT 1";
        try {
            Analytics record = clickhouseJdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Analytics(
                    rs.getLong("transaction_id"),
                    rs.getString("partner_id"),
                    rs.getLong("client_id"),
                    rs.getLong("amount"),
                    rs.getTimestamp("timestamp").toLocalDateTime()
            ), id);
            return Optional.ofNullable(record);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteTransaction(Long id) {
        // В ClickHouse удаление идеологически асинхронно через мутации (ALTER TABLE ... DELETE)
        String sql = "ALTER TABLE transactions DELETE WHERE transaction_id = ?";
        clickhouseJdbcTemplate.execute(sql);
    }
}