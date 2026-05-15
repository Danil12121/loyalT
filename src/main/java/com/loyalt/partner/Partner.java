package com.loyalt.partner;

import jakarta.persistence.*;

@Entity
@Table(name="partner_loyalty_rules")
public class Partner {

    @Id
    private String partnerId;
        
    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Double value;

    public Partner() {
    }

    public Partner(String partnerId, String type, double value) {
        this.partnerId = partnerId;
        this.type = type;
        this.value = value;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}