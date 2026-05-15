package com.loyalt.bankkernel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="loyalty")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
        
    @Column(name = "partnerId")
    private String partnerId;

    @Column(name = "value")
    private Double value;

    @Column(name = "loyalType")
    private String loyalType;

    @Column(name = "currValue")
    private int currValue;

    @Column(name = "maxValueorPercent")
    private int maxValueorPercent;

    public Payment() {
    }

    public Payment(String partnerName, Double value, 
               String loyalType, int currValue, int maxValueorPercent) {
        this.partnerId = partnerName;
        this.value = value;
        this.loyalType = loyalType;
        this.currValue = currValue;
        this.maxValueorPercent = maxValueorPercent;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public Double getValue() {
        return value;
    }

    public String getLoyalType() {
        return loyalType;
    }

    public int getCurrValue() {
        return currValue;
    }

    public int getMaxValueorPercent() {
        return maxValueorPercent;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setLoyalType(String loyalType) {
        this.loyalType = loyalType;
    }

    public void setCurrValue(int currValue) {
        this.currValue = currValue;
    }

    public void setMaxValueorPercent(int maxValueorPercent) {
        this.maxValueorPercent = maxValueorPercent;
    }
}