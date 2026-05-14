package com.loyalt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);  // тело запроса
        filter.setIncludeHeaders(true);   // все заголовки
        filter.setIncludeClientInfo(true); // клиентская информация
        filter.setMaxPayloadLength(10000);
        return filter;
    }
}