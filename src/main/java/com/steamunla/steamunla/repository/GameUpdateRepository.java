package com.steamunla.steamunla.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.GameUpdate;

@Repository
public interface GameUpdateRepository extends JpaRepository<GameUpdate, Long> {
    List<GameUpdate> findByGameOrderByReleaseDateDesc(Game game);
}