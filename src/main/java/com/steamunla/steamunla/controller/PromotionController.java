



package com.steamunla.steamunla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.repository.PromotionRepository;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionRepository promotionRepository;

    @GetMapping
    public String getAllPromotions(Model model) {

        model.addAttribute("promotions",
                promotionRepository.findAll());

        return "promotions/index";
    }

    @GetMapping("/game/{gameId}")
    public String getPromotionsByGame(
            @PathVariable Long gameId,
            Model model) {

        List<Promotion> promotions =
                promotionRepository.findByGameId(gameId);

        model.addAttribute("promotions", promotions);

        return "promotions/index";
    }
}