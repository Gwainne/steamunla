package com.steamunla.steamunla.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Benefit;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.service.BenefitService;

@Controller
@RequestMapping("/benefits")
public class BenefitController {

    private final BenefitService benefitService;
    private final GameRepository gameRepository;

    public BenefitController(BenefitService benefitService,
                             GameRepository gameRepository) {
        this.benefitService = benefitService;
        this.gameRepository = gameRepository;
    }

    // =========================
    // LISTAR BENEFICIOS
    // =========================

    @GetMapping("/game/{gameId}")
    public String list(@PathVariable Long gameId, Model model) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        model.addAttribute("game", game);
        model.addAttribute("gameId", gameId);
        model.addAttribute("benefits", benefitService.getByGameId(gameId));

        return "benefits/index";
    }

    // =========================
    // FORM NUEVO
    // =========================

    @GetMapping("/new/{gameId}")
    public String newForm(@PathVariable Long gameId, Model model) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        model.addAttribute("game", game);
        model.addAttribute("gameId", gameId);
        model.addAttribute("benefit", new Benefit());

        return "benefits/form";
    }

    // =========================
    // GUARDAR
    // =========================

    @PostMapping("/save/{gameId}")
    public String save(@PathVariable Long gameId,
                       @RequestParam String title,
                       @RequestParam String description) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        Benefit benefit = new Benefit();

        benefit.setTitle(title);
        benefit.setDescription(description);
        benefit.setGame(game);
        benefit.setActive(true);

        benefitService.save(benefit);

        return "redirect:/benefits/game/" + gameId;
    }

    // =========================
    // FORM EDIT
    // =========================

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        Benefit benefit = benefitService.getById(id);

        model.addAttribute("benefit", benefit);
        model.addAttribute("game", benefit.getGame());
        model.addAttribute("gameId", benefit.getGame().getId());

        return "benefits/form";
    }

    // =========================
    // UPDATE
    // =========================

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String description) {

        Benefit benefit = benefitService.getById(id);

        benefit.setTitle(title);
        benefit.setDescription(description);

        benefitService.save(benefit);

        return "redirect:/benefits/game/" + benefit.getGame().getId();
    }

    // =========================
    // DELETE
    // =========================

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        Benefit benefit = benefitService.getById(id);

        Long gameId = benefit.getGame().getId();

        benefitService.delete(id);

        return "redirect:/benefits/game/" + gameId;
    }
}