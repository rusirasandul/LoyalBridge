package com.example.LoyalBridge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class ConversionLog {

    @Id @GeneratedValue private Long id;

    @ManyToOne private User user;
    @ManyToOne private Partner partner;

    private int points;
    private LocalDateTime timestamp;
    private String status;


}
