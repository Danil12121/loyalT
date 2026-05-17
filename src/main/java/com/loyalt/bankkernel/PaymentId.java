package com.loyalt.bankkernel;


import java.io.Serializable;
import java.util.Objects;

public class PaymentId implements Serializable {
    private int clientId;
    private String partnerId;

    public PaymentId() {
    }

    public PaymentId(int clientId, String partnerId) {
        this.clientId = clientId;
        this.partnerId = partnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return clientId == paymentId.clientId && Objects.equals(partnerId, paymentId.partnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, partnerId);
    }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getPartnerId() { return partnerId; }
    public void setPartnerId(String partnerId) { this.partnerId = partnerId; }
}