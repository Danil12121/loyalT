package com.loyalt.bankkernel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="client_loyalty_data")
@IdClass(PaymentId.class)
public class Payment {


    @Id
    @Column(name = "client_id")
    private int clientId;

    @Id
    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "loyalty_type")
    private String loyaltyType;

    @Column(name = "curr_value")
    private int currValue;

    @Column(name = "max_value_or_percent")
    private int maxValueOrPercent;

    public Payment() {
    }

    public Payment(String partnerId, Double balance,
                   String loyaltyType, int currValue, int maxValueOrPercent) {
        this.partnerId = partnerId;
        this.balance = balance;
        this.loyaltyType = loyaltyType;
        this.currValue = currValue;
        this.maxValueOrPercent = maxValueOrPercent;
    }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getPartnerId() { return partnerId; }
    public void setPartnerId(String partnerId) { this.partnerId = partnerId; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getLoyaltyType() { return loyaltyType; }
    public void setLoyaltyType(String loyaltyType) { this.loyaltyType = loyaltyType; }

    public int getCurrValue() { return currValue; }
    public void setCurrValue(int currValue) { this.currValue = currValue; }

    public int getMaxValueOrPercent() { return maxValueOrPercent; }
    public void setMaxValueOrPercent(int maxValueOrPercent) { this.maxValueOrPercent = maxValueOrPercent; }
}