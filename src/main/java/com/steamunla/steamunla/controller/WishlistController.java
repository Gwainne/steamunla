package com.steamunla.steamunla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.model.Wishlist;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping
    public String showWishlist(Model model, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        List<Wishlist> wishlist = wishlistService.getWishlistByUser(user);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("user", user);
        return "wishlist/index";
    }

    @GetMapping("/add/{gameId}")
    public String addToWishlist(@PathVariable Long gameId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        User user = getLoggedUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);
        boolean alreadyInWishlist = wishlistService.isGameInWishlist(user, game);

        if (alreadyInWishlist) {
            redirectAttributes.addFlashAttribute("successMessage", "El juego ya está en tu lista de deseados.");
        } else {
            wishlistService.addGameToWishlist(user, game);
            redirectAttributes.addFlashAttribute("successMessage", "Juego agregado a tu lista de deseados.");
        }

        return "redirect:/wishlist";
    }

    @GetMapping("/remove/{gameId}")
    public String removeFromWishlist(@PathVariable Long gameId,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        User user = getLoggedUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);
        wishlistService.removeGameFromWishlist(user, game);
        redirectAttributes.addFlashAttribute("successMessage", "Juego eliminado de tu lista de deseados.");

        return "redirect:/wishlist";
    }
}
