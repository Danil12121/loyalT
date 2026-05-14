package com.loyalt.analytics;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="loyalty")
public class Analytics {

    @Id
    private String partnerId;
        
    @Column(name = "totalClients")
    private int totalClients;

    @Column(name = "totalTransactions")
    private Long totalTransactions;

    @Column(name = "newClients")
    private int newClients;

    @Column(name = "date")
    private LocalDateTime date;

    public Analytics() {
    }

    public Analytics(String partnerId, int totalClients, Long totalTransactions, int newClients, LocalDateTime date) {
        this.partnerId = partnerId;
        this.totalClients = totalClients;
        this.totalTransactions = totalTransactions;
        this.newClients = newClients;
        this.date = date;
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
}
