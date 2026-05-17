package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.PurchaseService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @GetMapping("/{gameId}")
    public String showCheckout(@PathVariable Long gameId, Model model, HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        model.addAttribute("game", game);
        model.addAttribute("user", user);

        return "purchases/checkout";
    }

    @PostMapping("/{gameId}")
    public String buyGame(@PathVariable Long gameId,
                          @RequestParam String paymentMethod,
                          Model model,
                          HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        purchaseService.buyGame(user, game, paymentMethod);

        model.addAttribute("game", game);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("user", user);

        return "purchase-success";
    }
}