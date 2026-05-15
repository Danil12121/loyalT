package com.loyalt.partner;


public class PartnerDeleteRequest {
    private String id;

    public PartnerDeleteRequest() {}

    public PartnerDeleteRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteRequest{id='" + id + "'}";
    }
}