package com.steamunla.steamunla.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Promotion;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.PromotionRepository;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private GameRepository gameRepository;

    // =========================
    // LISTAR
    // =========================
    @GetMapping("/game/{gameId}")
    public String list(@PathVariable Long gameId, Model model) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        List<Promotion> promotions = promotionRepository.findByGameId(gameId);

        model.addAttribute("game", game);
        model.addAttribute("promotions", promotions);

        return "promotions/index";
    }

    // =========================
    // FORM NUEVO
    // =========================
    @GetMapping("/new/{gameId}")
    public String newForm(@PathVariable Long gameId, Model model) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        model.addAttribute("game", game);
        model.addAttribute("promotion", new Promotion());

        return "promotions/form";
    }

    // =========================
    // CREATE (ARREGLADO)
    // =========================
    @PostMapping("/save/{gameId}")
    public String save(@PathVariable Long gameId,
                       @RequestParam Double discountPercent,
                       @RequestParam String description) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        Promotion p = new Promotion();
        p.setGame(game);
        p.setDiscountPercent(discountPercent);
        p.setDescription(description);
        p.setActive(true);

        // 🔥 FIX CRÍTICO DEL ERROR
        p.setStartDate(LocalDateTime.now());
        p.setEndDate(LocalDateTime.now().plusDays(7));

        promotionRepository.save(p);

        return "redirect:/promotions/game/" + gameId;
    }

    // =========================
    // FORM EDIT
    // =========================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Promotion p = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion no encontrada"));

        model.addAttribute("promotion", p);
        model.addAttribute("game", p.getGame());

        return "promotions/form";
    }

    // =========================
    // UPDATE
    // =========================
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam Double discountPercent,
                         @RequestParam String description) {

        Promotion p = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion no encontrada"));

        p.setDiscountPercent(discountPercent);
        p.setDescription(description);

        promotionRepository.save(p);

        return "redirect:/promotions/game/" + p.getGame().getId();
    }

    // =========================
    // DELETE
    // =========================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        Promotion p = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion no encontrada"));

        Long gameId = p.getGame().getId();

        promotionRepository.delete(p);

        return "redirect:/promotions/game/" + gameId;
    }
}