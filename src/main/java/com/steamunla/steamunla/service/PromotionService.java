package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public Optional<Promotion> getActivePromotion(Game game) {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findByGameAndActiveTrueAndStartDateBeforeAndEndDateAfter(game, now, now);
    }

    public double calculateDiscountedPrice(Game game) {
        Optional<Promotion> promotion = getActivePromotion(game);
        if (promotion.isPresent()) {
            double discount = promotion.get().getDiscountPercent() / 100.0;
            double discounted = game.getPrice() * (1 - discount);
            return Math.round(discounted * 100.0) / 100.0;
        }
        return game.getPrice();
    }

    public List<Promotion> getPromotionsByGame(Game game) {
        return promotionRepository.findByGame(game);
    }

    public Promotion createPromotion(Game game, double discountPercent,
                                      LocalDateTime startDate, LocalDateTime endDate,
                                      String description) {
        Promotion promo = new Promotion();
        promo.setGame(game);
        promo.setDiscountPercent(discountPercent);
        promo.setStartDate(startDate);
        promo.setEndDate(endDate);
        promo.setDescription(description);
        promo.setActive(true);
        return promotionRepository.save(promo);
    }

    public void deactivatePromotion(Long promotionId) {
        promotionRepository.findById(promotionId).ifPresent(promo -> {
            promo.setActive(false);
            promotionRepository.save(promo);
        });
    }
}
