package com.loyalt.analytics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ClickHouseConfig {

    @Value("${clickhouse.datasource.url}")
    private String url;

    @Value("${clickhouse.datasource.username}")
    private String username;

    @Value("${clickhouse.datasource.password}")
    private String password;

    @Bean(name = "clickhouseJdbcTemplate")
    public JdbcTemplate clickhouseJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        // Используем проверенный класс Яндекс-драйвера
        dataSource.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
        
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }
}