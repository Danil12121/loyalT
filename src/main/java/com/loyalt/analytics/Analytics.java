package com.loyalt.analytics;

import java.time.LocalDateTime;

public class Analytics {

    private Long transactionId;
    private String partnerId;
    private int clientId;
    private Long amount;
    private LocalDateTime date;

    public Analytics() {
    }

    public Analytics(Long transactionId, String partnerId, int clientId, Long amount, LocalDateTime date) {
        this.transactionId = transactionId;
        this.partnerId = partnerId;
        this.clientId = clientId;
        this.amount = amount;
        this.date = date;
    }

    public Analytics(String partnerId, int clientId, Long amount, LocalDateTime date) {
        this.partnerId = partnerId;
        this.clientId = clientId;
        this.amount = amount;
        this.date = date;
    }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public String getPartnerId() { return partnerId; }
    public void setPartnerId(String partnerId) { this.partnerId = partnerId; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}