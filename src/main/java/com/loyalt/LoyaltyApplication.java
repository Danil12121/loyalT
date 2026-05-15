package com.loyalt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class LoyaltyApplication {

    private static final Logger log = LoggerFactory.getLogger(LoyaltyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyApplication.class, args);
    }

    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        log.info(" Loyalty Application has been started!");
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        String port = env.getProperty("server.port", "8085");
        String contextPath = env.getProperty("server.servlet.context-path", "");

        log.info("=".repeat(60));
        log.info(" LOYALTY SERVICE IS READY!");
        log.info(" Server listening on: http://localhost:{}{}", port, contextPath);
        log.info(" API available at: http://localhost:{}{}/api/partners", port, contextPath);
        log.info(" Ready at: {}", new java.util.Date());
        log.info("=".repeat(60));
    }

    @EventListener
    public void onWebServerInitialized(WebServerInitializedEvent event) {
        log.info(" Web server initialized on port: {}", event.getWebServer().getPort());
        log.info(" Server is now ACCEPTING CONNECTIONS from clients...");
    }

    @EventListener
    public void onApplicationFailed(ApplicationFailedEvent event) {
        log.error(" Application failed to start!", event.getException());
    }
}