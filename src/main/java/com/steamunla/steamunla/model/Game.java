package com.steamunla.steamunla.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "games")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String genre;

    private Double price;

    private String imageUrl;

    private String developerName;  // nombre de la empresa desarrolladora

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private User publisher;        // usuario que publicó el juego (rol DEVELOPER)

    private LocalDateTime releaseDate;

    private LocalDateTime createdAt;

    private boolean active;

    private Double averageRating;

    private Long downloadCount;
}