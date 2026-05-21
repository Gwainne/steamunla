package com.steamunla.steamunla.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByGameAndActiveTrueAndStartDateBeforeAndEndDateAfter(Game game, LocalDateTime now1, LocalDateTime now2);

    List<Promotion> findByGame(Game game);
}
