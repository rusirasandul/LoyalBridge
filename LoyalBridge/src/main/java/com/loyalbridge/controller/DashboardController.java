package com.loyalbridge.controller;

import com.loyalbridge.model.Transaction;
import com.loyalbridge.service.TransactionService;
import com.loyalbridge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard Overview", description = "Dashboard APIs")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'FINANCE_TEAM', 'SUPPORT_STAFF', 'PARTNER_ADMIN')")
    @Operation(summary = "Get dashboard overview stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getAllUsers(PageRequest.of(0, 1)).getTotalElements());
        stats.put("totalPoints", transactionService.getTotalPoints());
        stats.put("totalConvertedAmount", transactionService.getTotalConvertedAmount());
        List<Transaction> last10 = transactionService.getTransactions(null, null, null, null, PageRequest.of(0, 10)).getContent();
        stats.put("last10Transactions", last10);
        return ResponseEntity.ok(stats);
    }
} 