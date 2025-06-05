package com.example.LoyalBridge.model;

import jakarta.persistence.*;

@Entity
public class AdminUser {
    @Id @GeneratedValue private Long id;

    @Column(unique = true)
    @Pattern(regexp = ".*@LoyalBridge\\.io$")

    private String email;

    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean otpEnabled;
}
