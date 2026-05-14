package com.steamunla.steamunla.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "library")
@Data
public class Library {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;          // ← objeto User completo, no solo el id

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;          // ← objeto Game completo, no solo el id

    private boolean installed;
    private LocalDateTime addedAt;
    private LocalDateTime lastPlayed;
}