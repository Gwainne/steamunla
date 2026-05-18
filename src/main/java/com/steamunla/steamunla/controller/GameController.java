package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.ReviewService;
import com.steamunla.steamunla.service.PurchaseService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.steamunla.steamunla.model.Purchase;
import com.steamunla.steamunla.model.Review;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PurchaseService purchaseService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping
    public String listGames(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("user", user);

        // Si el usuario está logueado, obtener IDs de juegos que ya posee
        Set<Long> ownedGameIds = new HashSet<>();
        if (user != null) {
            // Obtener todos los juegos comprados por el usuario
            List<Purchase> purchases = purchaseService.getGamesPurchasedByUser(user);
            ownedGameIds = purchases.stream()
                    .map(p -> p.getGame().getId())
                    .collect(Collectors.toSet());
        }

        model.addAttribute("ownedGameIds", ownedGameIds);

        return "games/index";
    }

    @GetMapping("/{id}")
    public String showGameDetail(@PathVariable Long id, Model model, HttpSession session) {

        User user = getLoggedUser(session);

        Game game = gameService.getGameById(id);
        List<Review> reviews = reviewService.getReviewsByGame(game);

        // Verificar si el usuario ya posee el juego
        boolean alreadyOwned = false;
        if (user != null) {
            alreadyOwned = purchaseService.hasUserAlreadyBoughtGame(user, game);
        }

        model.addAttribute("game", game);
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", user);
        model.addAttribute("alreadyOwned", alreadyOwned);

        return "games/detail";
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