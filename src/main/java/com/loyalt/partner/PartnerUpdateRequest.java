package com.loyalt.partner;

public class PartnerUpdateRequest {
    private String id;
    private String type;
    private Double value;

    public PartnerUpdateRequest() {}

    public PartnerUpdateRequest(String id, String type, Double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PartnerUpdateRequest{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}