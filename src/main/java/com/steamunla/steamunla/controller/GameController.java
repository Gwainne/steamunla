package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.service.GameService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String listGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "games/index";
    }

    @GetMapping("/new")
    public String showPublishForm(Model model) {
        if (!model.containsAttribute("game")) {
            model.addAttribute("game", new Game());
        }
        return "games/new";
    }

    @PostMapping
    public String publishGame(@Valid @ModelAttribute("game") Game game,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("game", game);
            return "games/new";
        }

        Game savedGame = gameService.publishGame(game);
        redirectAttributes.addFlashAttribute("successMessage",
                "Juego publicado: " + savedGame.getTitle());
        return "redirect:/games";
    }
}