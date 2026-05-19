
<<<<<<< HEAD
=======

>>>>>>> 8d20162c5214d4237d411bf6ccb8a900c67efd97
package com.steamunla.steamunla.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "benefits")
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private boolean active;

    private LocalDateTime createdAt;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

=======
>>>>>>> 8d20162c5214d4237d411bf6ccb8a900c67efd97
    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
<<<<<<< HEAD

    // NUEVO

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
=======
}
>>>>>>> 8d20162c5214d4237d411bf6ccb8a900c67efd97
