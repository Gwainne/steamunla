package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.repository.BenefitRepository;

@Controller
@RequestMapping("/benefits")
public class BenefitController {

    @Autowired
    private BenefitRepository benefitRepository;

    // =========================
    // LISTAR TODOS LOS BENEFICIOS
    // =========================
    @GetMapping
    public String getAllBenefits(Model model) {

        model.addAttribute(
                "benefits",
                benefitRepository.findAll()
        );

        return "benefits/index";
    }

    // =========================
    // BENEFICIOS POR JUEGO
    // =========================
    @GetMapping("/game/{gameId}")
    public String getBenefitsByGame(
            @PathVariable Long gameId,
            Model model) {

        model.addAttribute(
                "benefits",
                benefitRepository.findByGameId(gameId)
        );

        return "benefits/index";
    }
}