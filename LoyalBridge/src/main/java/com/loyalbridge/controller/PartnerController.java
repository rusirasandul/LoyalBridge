package com.loyalbridge.controller;

import com.loyalbridge.model.Partner;
import com.loyalbridge.model.Transaction;
import com.loyalbridge.service.PartnerService;
import com.loyalbridge.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/partners")
@Tag(name = "Partner Management", description = "Partner management APIs")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PARTNER_ADMIN')")
    @Operation(summary = "List all partners")
    public ResponseEntity<Page<Partner>> listPartners(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(partnerService.getAllPartners(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PARTNER_ADMIN')")
    @Operation(summary = "Get partner by ID")
    public ResponseEntity<Partner> getPartner(@PathVariable Long id) {
        Optional<Partner> partner = partnerService.getPartnerById(id);
        return partner.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Add a new partner")
    public ResponseEntity<Partner> addPartner(@RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.createPartner(partner));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Edit a partner")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.updatePartner(id, partner));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Delete a partner")
    public ResponseEntity<?> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/enable")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Enable partner integration")
    public ResponseEntity<?> enablePartner(@PathVariable Long id) {
        partnerService.enablePartner(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disable")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Disable partner integration")
    public ResponseEntity<?> disablePartner(@PathVariable Long id) {
        partnerService.disablePartner(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PARTNER_ADMIN')")
    @Operation(summary = "View partner-specific transactions")
    public ResponseEntity<Page<Transaction>> getPartnerTransactions(@PathVariable Long id,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(transactionService.getTransactions(null, id, null, null, pageable));
    }
} 