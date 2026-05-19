package com.steamunla.steamunla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.GameUpdate;

@Repository
public interface GameUpdateRepository extends JpaRepository<GameUpdate, Long> {

    // 📌 Todos los parches de un juego
    List<GameUpdate> findByGame(Game game);

    // 📌 Ordenados por fecha (más nuevo primero)
    List<GameUpdate> findByGameOrderByReleaseDateDesc(Game game);

    // 📌 Último parche del juego
    GameUpdate findTopByGameOrderByReleaseDateDesc(Game game);

    // 📌 Buscar por versión exacta
    List<GameUpdate> findByVersion(String version);

    // 📌 Buscar por juego + versión
    GameUpdate findByGameAndVersion(Game game, String version);
}