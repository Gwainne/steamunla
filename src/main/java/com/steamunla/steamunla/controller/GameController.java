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
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping
    public String listGames(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("user", user);

        return "games/index";
    }

    @GetMapping("/new")
    public String showPublishForm(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        if (!model.containsAttribute("game")) {
            model.addAttribute("game", new Game());
        }

        model.addAttribute("user", user);

        return "games/new";
    }

    @PostMapping
    public String publishGame(@Valid @ModelAttribute("game") Game game,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("game", game);
            model.addAttribute("user", user);
            return "games/new";
        }

        Game savedGame = gameService.publishGame(game);
        redirectAttributes.addFlashAttribute("successMessage",
                "Juego publicado: " + savedGame.getTitle());

        return "redirect:/games";
    }
}