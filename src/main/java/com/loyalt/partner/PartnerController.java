package com.loyalt.partner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private static final Logger log = LoggerFactory.getLogger(PartnerController.class);
    private final PartnerService service;

    public PartnerController(PartnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Partner> getAll() {
        log.info("REST request to get all partners");
        List<Partner> partners = service.getAll();
        log.info("Retrieved {} partners", partners != null ? partners.size() : 0);
        return partners;
    }

    @PostMapping
    public Partner create(@NonNull @RequestBody Partner partner) {
        log.info("REST request to create partner: {}", partner);

        Partner created = service.create(partner);
        log.info("Partner created successfully");
        return created;
    }

    @PutMapping
    public String update(@NonNull @RequestBody PartnerUpdateRequest updateRequest) {
        log.info("REST request to update partner - body: {}", updateRequest);

        if (updateRequest.getId() == null || updateRequest.getId().trim().isEmpty()) {
            log.error("Update failed: partner id is null or empty");
            return "Update failed: invalid id";
        }

        try {
            service.update(updateRequest.getId(),
                    updateRequest.getType(),
                    updateRequest.getValue());
            log.info("Partner {} updated successfully (type: {}, value: {})",
                    updateRequest.getId(),
                    updateRequest.getType(),
                    updateRequest.getValue());
            return "Updated successfully";
        } catch (Exception e) {
            log.error("Failed to update partner {}: {}", updateRequest.getId(), e.getMessage(), e);
            return "Update failed: " + e.getMessage();
        }
    }

    @DeleteMapping
    public void delete(@NonNull @RequestBody PartnerDeleteRequest deleteRequest) {
        log.info("REST request to delete partner with id: {}", deleteRequest.getId());

        if (deleteRequest.getId() == null || deleteRequest.getId().trim().isEmpty()) {
            log.warn("Delete attempt with null or empty id");
            return;
        }

        try {
            service.delete(deleteRequest.getId());
            log.info("Partner {} deleted successfully", deleteRequest.getId());
        } catch (Exception e) {
            log.error("Failed to delete partner {}: {}", deleteRequest.getId(), e.getMessage(), e);
            throw e;
        }
    }
}