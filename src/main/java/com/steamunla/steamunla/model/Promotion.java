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

@Entity
@Table(name = "promotions")
public class Promotion 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private Double discountPercent;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private String description;

    private boolean active;

    // ===== GETTERS Y SETTERS =====

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Game getGame() 
    {
        return game;
    }

    public void setGame(Game game) 
    {
        this.game = game;
    }

    public Double getDiscountPercent() 
    {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) 
    {
        this.discountPercent = discountPercent;
    }

    public LocalDateTime getStartDate() 
    {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) 
    {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() 
    {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate)
    {
        this.endDate = endDate;
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
}