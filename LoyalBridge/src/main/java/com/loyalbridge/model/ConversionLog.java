package com.loyalbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "conversion_logs")
public class ConversionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Partner is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @NotNull(message = "Points are required")
    @Positive(message = "Points must be positive")
    private int points;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp = LocalDateTime.now();

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private ConversionStatus status = ConversionStatus.PENDING;
} 