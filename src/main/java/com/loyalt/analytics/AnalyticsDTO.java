package com.loyalt.analytics;

import java.time.LocalDateTime;

public class AnalyticsDTO {
    private String partnerId;
    private LocalDateTime date;
    private long totalUsers;
    private long totalTransactions;
    private long newUsers;

    public AnalyticsDTO(String partnerId, LocalDateTime date, long totalUsers, long totalTransactions, long newUsers) {
        this.partnerId = partnerId;
        this.date = date;
        this.totalUsers = totalUsers;
        this.totalTransactions = totalTransactions;
        this.newUsers = newUsers;
    }

    public String getPartnerId() { return partnerId; }
    public void setPartnerId(String partnerId) { this.partnerId = partnerId; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }

    public long getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(long totalTransactions) { this.totalTransactions = totalTransactions; }

    public long getNewUsers() { return newUsers; }
    public void setNewUsers(long newUsers) { this.newUsers = newUsers; }
}
