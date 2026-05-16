package com.loyalt.bankkernel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="client_loyalty_data")
public class Payment {

    @Id
    @Column(name = "client_id")
    private int clientId;
        
    @Column(name = "partnerId")
    private String partnerId;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "loyaltyType")
    private String loyaltyType;

    @Column(name = "currValue")
    private int currValue;

    @Column(name = "maxValueOrPercent")
    private int maxValueOrPercent;

    public Payment() {
    }

    public Payment(String partnerName, Double value, 
               String loyalType, int currValue, int maxValueorPercent) {
        this.partnerId = partnerName;
        this.balance = value;
        this.loyaltyType = loyalType;
        this.currValue = currValue;
        this.maxValueOrPercent = maxValueorPercent;
    }

    public int getClientId() {
        return clientId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public Double getBalance() {
        return balance;
    }

    public String getLoyaltyType() {
        return loyaltyType;
    }

    public int getCurrValue() {
        return currValue;
    }

    public int getMaxValueOrPercent() {
        return maxValueOrPercent;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setLoyaltyType(String loyalType) {
        this.loyaltyType = loyalType;
    }

    public void setCurrValue(int currValue) {
        this.currValue = currValue;
    }

    public void setMaxValueOrPercent(int maxValueOrPercent) {
        this.maxValueOrPercent = maxValueOrPercent;
    }
}