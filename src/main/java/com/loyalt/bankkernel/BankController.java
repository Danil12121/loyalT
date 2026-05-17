package com.loyalt.bankkernel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bank")
public class BankController {
    private static final Logger log = LoggerFactory.getLogger(BankController.class);
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping
    public List<Payment> getAll() {
        log.info("REST request to get all partners analytics");
        List<Payment> payments = service.getAll();
        log.info("Retrieved {} partners", payments != null ? payments.size() : 0);
        return payments;
    }

    @PostMapping
    public Payment create(@org.springframework.lang.NonNull @RequestBody Payment payment) {
        log.info("REST request to create partner analytics: {}", payment);

        Payment created = service.save(payment);
        log.info("Partner analytics created successfully");
        return created;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String partnerId, @RequestBody UpdatePaymentRequest updatePaymentRequest) {

        log.info("REST request to update partner payments - id: {}, body: {}", partnerId, updatePaymentRequest);

        if (partnerId == null || partnerId.trim().isEmpty()) {
            log.error("Update failed: partner id is null or empty");
            return "Update failed: invalid id";
        }

        try {
            Payment paymentToUpdate = new Payment(partnerId, updatePaymentRequest.getValue(), updatePaymentRequest.getLoyalType(), updatePaymentRequest.getCurrValue(), updatePaymentRequest.getMaxValueorPercent());
            service.save(paymentToUpdate);

            log.info("Partner {} analytics updated successfully (clients: {}, new clients: {}, total tranactions: {}, date: {})", updatePaymentRequest.getClientId(), partnerId, updatePaymentRequest.getLoyalType(), updatePaymentRequest.getValue(), updatePaymentRequest.getCurrValue(), updatePaymentRequest.getMaxValueorPercent());
            return "Updated successfully";
        } catch (Exception e) {
            log.error("Failed to update partner {}: {}", partnerId, e.getMessage(), e);
            return "Update failed: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable String partnerId, int clientId) {
        log.info("REST request to delete payment with id: {}", clientId);

        if (partnerId == null || partnerId.trim().isEmpty()) {
            log.warn("Delete attempt with null or empty partnerId");
            return;
        }

        try {
            service.delete(partnerId, clientId);
            log.info("Payment by {} deleted successfully", clientId);
        } catch (Exception e) {
            log.error("Failed to delete payment {}: {}", clientId, e.getMessage(), e);
            throw e;
        }
    }
}

class UpdatePaymentRequest {

    private Long clientId;
    private String partnerId;
    private Double value;
    private String loyalType;
    private int currValue;
    private int maxValueOrPercent;

    public UpdatePaymentRequest() {
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
        return maxValueOrPercent;
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

    public void setMaxValueOrPercent(int maxValueorPercent) {
        this.maxValueOrPercent = maxValueorPercent;
    }

    @Override
    public String toString() {
        return "UpdatePaymentRequest{" + "partnerId='" + partnerId + '\'' + ", value=" + value + '\'' + ", loyalType=" + loyalType + '\'' + ", currValue=" + currValue + '\'' + ", maxValueOrPercent=" + maxValueOrPercent + '}';
    }
}