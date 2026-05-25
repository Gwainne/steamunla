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
    private GameUpdateRepository gameUpdateRepository;

    public List<GameUpdate> getUpdatesByGame(Game game) {
        return gameUpdateRepository.findByGameOrderByReleaseDateDesc(game);
    }

    public GameUpdate publishUpdate(Game game, String version, String patchNotes) {
        GameUpdate update = new GameUpdate();
        update.setGame(game);
        update.setVersion(version);
        update.setPatchNotes(patchNotes);
        update.setReleaseDate(LocalDateTime.now());
        return gameUpdateRepository.save(update);
    }
}