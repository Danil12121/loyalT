package com.loyalt.partner;

import jakarta.persistence.*;

@Entity
@Table(name="loyalty")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerId;
        
    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Double value;

    public Partner() {
    }

    public Partner(Long partnerId, String type, double value) {
        this.partnerId = partnerId;
        this.type = type;
        this.value = value;
    }

    public Long getPartnerId() {
            return partnerId;
    }

    public void setPartnerId(Long partnerId) {
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