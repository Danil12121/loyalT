package com.loyalt.analytics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

@Repository
public class AnalyticsRepository {

    private final JdbcTemplate clickHouseJdbcTemplate;

    public AnalyticsRepository(@Qualifier("clickhouseJdbcTemplate") JdbcTemplate clickHouseJdbcTemplate) {
        this.clickHouseJdbcTemplate = clickHouseJdbcTemplate;
    }

    public void save(Analytics analytics) {
        String sql = "INSERT INTO analytics (transaction_id, partner_id, client_id, amount, date) VALUES (?, ?, ?, ?, ?)";

        clickHouseJdbcTemplate.update(sql,
                analytics.getTransactionId(),
                analytics.getPartnerId(),
                analytics.getClientId(),
                analytics.getAmount(),
                analytics.getDate() != null ? Timestamp.valueOf(analytics.getDate()) : null
        );
    }
}