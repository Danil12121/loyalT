package com.loyalt.bankkernel;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class BankController {
    // принимает оплату клиента, считает сколько баллов лояльности дать
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Payment create(@org.springframework.lang.NonNull@RequestBody Payment payment) {
        return service.create(payment);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long clientId, 
                    @RequestParam String partnerId, 
                    @RequestParam String type,
                    @RequestParam Double value,
                    @RequestParam int currValue, 
                    @RequestParam int maxValueorPercent) {
        service.update(clientId, partnerId, type, value, currValue, maxValueorPercent);
        return "Updated successfully";
    }

    @DeleteMapping("/{id}")
    public void delete(Long clientId) {
        service.deletePayment(clientId);
    }
}
