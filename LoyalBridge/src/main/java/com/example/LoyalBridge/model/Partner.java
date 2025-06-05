package com.example.LoyalBridge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
public class Partner {

    @Id @GeneratedValue private Long id;

    private String name;
    private String email;
    private String authMethod;

    private double conversationRate;

    private boolean active;

}
