package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAllByOrderByCreatedAtDesc();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el juego"));
    }

    public Game publishGame(Game game) {
        return publishGame(null, game);
    }

    public Game publishGame(User publisher, Game game) {
        game.setPublisher(publisher);
        game.setCreatedAt(LocalDateTime.now());
        game.setActive(true);
        if (game.getDownloadCount() == null) {
            game.setDownloadCount(0L);
        }
        if (game.getAverageRating() == null) {
            game.setAverageRating(0.0);
        }
        return gameRepository.save(game);
    }

    public boolean hasUserPublishedGames(User user) {
        return gameRepository.existsByPublisher(user);
    }

    public List<Game> getGamesByPublisher(User user) {
        return gameRepository.findByPublisher(user);
    }
}