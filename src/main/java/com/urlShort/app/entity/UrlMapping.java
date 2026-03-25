package com.urlShort.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortCode;

    @Column(length = 1000)
    private String longUrl;

    private LocalDateTime createdAt;
    private LocalDateTime expiryTime;
    private int clickCount;
}
