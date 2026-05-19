package com.steamunla.steamunla.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "benefits")
public class Benefit 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private boolean active;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // 🔥 IMPORTANTE: se ejecuta antes de guardar en DB
    @PrePersist
    public void prePersist() 
    {
        if (createdAt == null) 
        {
            createdAt = LocalDateTime.now();
        }
        // valor por defecto seguro
        active = true;
    }

    // getters y setters

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public boolean isActive() 
    {
        return active;
    }

    public void setActive(boolean active) 
    {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() 
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public Game getGame() 
    {
        return game;
    }

    public void setGame(Game game) 
    {
        this.game = game;
    }
}