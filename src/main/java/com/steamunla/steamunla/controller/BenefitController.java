package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Benefit;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.repository.BenefitRepository;
import com.steamunla.steamunla.repository.GameRepository;

@Controller
@RequestMapping("/benefits")
public class BenefitController 
{
    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private GameRepository gameRepository;

    // =========================
    // LISTAR POR JUEGO
    // =========================

    @GetMapping("/game/{gameId}")
    public String list(@PathVariable Long gameId, Model model) 
    {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        model.addAttribute("game", game);
        model.addAttribute("benefits", benefitRepository.findByGameId(gameId));

        return "benefits/index";
    }

    // =========================
    // FORM NUEVO
    // =========================

    @GetMapping("/new/{gameId}")
    public String newForm(@PathVariable Long gameId, Model model) 
    {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        model.addAttribute("gameId", gameId);
        model.addAttribute("game", game);
        model.addAttribute("benefit", null);

        return "benefits/form";
    }

    // =========================
    // GUARDAR
    // =========================

    @PostMapping("/save/{gameId}")
    public String save(@PathVariable Long gameId,
                       @RequestParam String title,
                       @RequestParam String description) 
    {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game no encontrado"));

        Benefit b = new Benefit();

        b.setTitle(title);
        b.setDescription(description);
        b.setActive(true);
        b.setGame(game);

        benefitRepository.save(b);

        return "redirect:/benefits/game/" + gameId;
    }

    // =========================
    // FORM EDIT
    // =========================

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) 
    {
        Benefit b = benefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit no encontrado"));

        model.addAttribute("benefit", b);
        model.addAttribute("gameId", b.getGame().getId());
        model.addAttribute("game", b.getGame());

        return "benefits/form";
    }

    // =========================
    // UPDATE
    // =========================
    @PostMapping("/update/{id}")

    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String description) 
    {

        Benefit b = benefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit no encontrado"));

        b.setTitle(title);
        b.setDescription(description);

        benefitRepository.save(b);

        return "redirect:/benefits/game/" + b.getGame().getId();
    }

    // =========================
    // DELETE
    // =========================

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) 
    {
        Benefit b = benefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit no encontrado"));

        Long gameId = b.getGame().getId();

        benefitRepository.delete(b);

        return "redirect:/benefits/game/" + gameId;
    }
}