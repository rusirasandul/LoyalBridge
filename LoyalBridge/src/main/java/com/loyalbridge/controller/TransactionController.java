package com.loyalbridge.controller;

import com.loyalbridge.model.Transaction;
import com.loyalbridge.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Conversion Log Viewer", description = "Conversion log APIs")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'FINANCE_TEAM', 'SUPPORT_STAFF', 'PARTNER_ADMIN')")
    @Operation(summary = "View point conversion transactions with filters and pagination")
    public ResponseEntity<Page<Transaction>> getTransactions(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(transactionService.getTransactions(userId, partnerId, startDate, endDate, pageable));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'FINANCE_TEAM', 'SUPPORT_STAFF', 'PARTNER_ADMIN')")
    @Operation(summary = "Export conversion logs as CSV")
    public void exportTransactionsAsCsv(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");
        Pageable pageable = Pageable.unpaged();
        Page<Transaction> transactions = transactionService.getTransactions(userId, partnerId, startDate, endDate, pageable);
        try (PrintWriter writer = response.getWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("ID", "User ID", "Partner ID", "Points", "Converted Amount", "Status", "Created At"))) {
            for (Transaction t : transactions.getContent()) {
                csvPrinter.printRecord(
                        t.getId(),
                        t.getUser().getId(),
                        t.getPartner().getId(),
                        t.getPoints(),
                        t.getConvertedAmount(),
                        t.getStatus(),
                        t.getCreatedAt()
                );
            }
        }
    }
} 