package com.steamunla.steamunla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.steamunla.steamunla.model.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    // 🔥 IMPORTANTE: necesario para /benefits/game/{id}
    List<Benefit> findByGameId(Long gameId);
}