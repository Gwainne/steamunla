
package com.steamunla.steamunla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getByGame(Long gameId) {
        return promotionRepository.findByGameId(gameId);
    }

    public Promotion getById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion no encontrada"));
    }

    public Promotion save(Promotion p) {
        return promotionRepository.save(p);
    }

    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }
}