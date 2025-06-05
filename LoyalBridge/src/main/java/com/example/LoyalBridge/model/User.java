package com.example.LoyalBridge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class User {

    private String name;
    private int Phone;
    private int points;

    @Enumerated(EnumType.STRING)

    private boolean highRisk;


}
