
package com.steamunla.steamunla.repository;

import com.steamunla.steamunla.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    // 📌 LISTAR POR JUEGO
    List<Benefit> findByGameId(Long gameId);

    // 📌 LISTAR ACTIVOS
    List<Benefit> findByActiveTrue();
}