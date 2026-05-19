package com.steamunla.steamunla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

=======
>>>>>>> 8d20162c5214d4237d411bf6ccb8a900c67efd97
import com.steamunla.steamunla.model.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {

<<<<<<< HEAD
    // Beneficios por juego
    List<Benefit> findByGameId(Long gameId);

    // Beneficios activos
    List<Benefit> findByActiveTrue();
=======
    // 🔥 IMPORTANTE: necesario para /benefits/game/{id}
    List<Benefit> findByGameId(Long gameId);
>>>>>>> 8d20162c5214d4237d411bf6ccb8a900c67efd97
}