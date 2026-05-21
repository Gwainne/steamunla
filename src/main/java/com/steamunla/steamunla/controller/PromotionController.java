package com.steamunla.steamunla.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.PromotionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping("/new/{gameId}")
    public String showNewPromotionForm(@PathVariable Long gameId, Model model, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/login";

        Game game = gameService.getGameById(gameId);

        if (game.getPublisher() == null || !game.getPublisher().getId().equals(user.getId())) {
            return "redirect:/games/my-games";
        }

        model.addAttribute("game", game);
        model.addAttribute("user", user);
        return "promotions/new";
    }

    @PostMapping("/create")
    public String createPromotion(@RequestParam Long gameId,
                                   @RequestParam double discountPercent,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate,
                                   @RequestParam(required = false) String description,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/login";

        Game game = gameService.getGameById(gameId);

        if (game.getPublisher() == null || !game.getPublisher().getId().equals(user.getId())) {
            return "redirect:/games/my-games";
        }

        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");

        promotionService.createPromotion(game, discountPercent, start, end, description);
        redirectAttributes.addFlashAttribute("successMessage", "Promoción creada exitosamente");
        return "redirect:/games/my-games";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivatePromotion(@PathVariable Long id, HttpSession session) {
        User user = getLoggedUser(session);
        if (user == null) return "redirect:/login";
        promotionService.deactivatePromotion(id);
        return "redirect:/games/my-games";
    }
}
