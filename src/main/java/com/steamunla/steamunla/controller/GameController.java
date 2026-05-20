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
import com.steamunla.steamunla.service.LibraryService;
import com.steamunla.steamunla.service.WishlistService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
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

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private WishlistService wishlistService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping
    public String listGames(Model model, HttpSession session) {

        User user = getLoggedUser(session);

        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("user", user);

        // Si el usuario está logueado, obtener IDs de juegos que siguen en su biblioteca
        Set<Long> ownedGameIds = Set.of();
        Set<Long> wishlistGameIds = new HashSet<>();
        if (user != null) {
            ownedGameIds = libraryService.getLibraryByUser(user).stream()
                .map(entry -> entry.getGame().getId())
                    .collect(Collectors.toSet());

            wishlistGameIds = wishlistService.getWishlistByUser(user).stream()
                    .map(w -> w.getGame().getId())
                    .collect(Collectors.toSet());
        }

        model.addAttribute("ownedGameIds", ownedGameIds);
        model.addAttribute("wishlistGameIds", wishlistGameIds);

        return "games/index";
    }

    @GetMapping("/{id}")
    public String showGameDetail(@PathVariable Long id, Model model, HttpSession session) {

        User user = getLoggedUser(session);

        Game game = gameService.getGameById(id);
        List<Review> reviews = reviewService.getReviewsByGame(game);

        // Verificar si el usuario tiene el juego en la biblioteca activa
        boolean alreadyOwned = false;
        if (user != null) {
            alreadyOwned = libraryService.isGameInLibrary(user, game);
        }

        boolean inWishlist = false;
        if (user != null) {
            inWishlist = wishlistService.isGameInWishlist(user, game);
        }

        model.addAttribute("game", game);
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", user);
        model.addAttribute("alreadyOwned", alreadyOwned);
        model.addAttribute("isInWishlist", inWishlist);

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