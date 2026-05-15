package com.loyalt.partner;

// DTO class for update request
class UpdateRequest {
    private String type;
    private Double value;

    // Default constructor
    public UpdateRequest() {
    }

    // Getters and setters
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
        return "UpdateRequest{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
