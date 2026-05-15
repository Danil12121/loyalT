package com.loyalt.partner;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    public Partner create(@org.springframework.lang.NonNull @RequestBody Partner partner) {
        log.info("REST request to create partner: {}", partner);

        Partner created = service.create(partner);
        log.info("Partner created successfully");
        return created;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id,
                         @RequestBody UpdateRequest updateRequest) {
        log.info("REST request to update partner - id: {}, body: {}", id, updateRequest);

        if (id == null || id.trim().isEmpty()) {
            log.error("Update failed: partner id is null or empty");
            return "Update failed: invalid id";
        }

        try {
            service.update(id, updateRequest.getType(), updateRequest.getValue());
            log.info("Partner {} updated successfully (type: {}, value: {})",
                    id, updateRequest.getType(), updateRequest.getValue());
            return "Updated successfully";
        } catch (Exception e) {
            log.error("Failed to update partner {}: {}", id, e.getMessage(), e);
            return "Update failed: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("REST request to delete partner with id: {}", id);

        if (id == null || id.trim().isEmpty()) {
            log.warn("Delete attempt with null or empty id");
            return;
        }

        try {
            service.delete(id);
            log.info("Partner {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Failed to delete partner {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}