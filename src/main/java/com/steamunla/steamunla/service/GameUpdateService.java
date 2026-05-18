package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.GameUpdate;
import com.steamunla.steamunla.repository.GameUpdateRepository;

@Service
public class GameUpdateService {

    @Autowired
    private GameUpdateRepository repo;

    public List<GameUpdate> getUpdatesByGame(Game game) {
        return repo.findByGame(game);
    }

    public GameUpdate createUpdate(Game game, String version, String notes) {
        GameUpdate update = new GameUpdate();
        update.setGame(game);
        update.setVersion(version);
        update.setPatchNotes(notes);
        update.setReleaseDate(LocalDateTime.now());

        return repo.save(update);
    }
}