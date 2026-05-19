
package com.steamunla.steamunla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steamunla.steamunla.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> 
{
    List<Promotion> findByGameId(Long gameId);
}

