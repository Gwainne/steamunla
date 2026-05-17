package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.UserRepository;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    private User getMockUser() {
        return userRepository.findByUsername("matiA").orElseThrow();
    }

    @GetMapping("/{gameId}")
    public String showCheckout(@PathVariable Long gameId, Model model) {

        Game game = gameService.getGameById(gameId);

        model.addAttribute("game", game);

        return "purchases/checkout";
    }

    @PostMapping("/{gameId}")
public String buyGame(@PathVariable Long gameId,
                      @RequestParam String paymentMethod,
                      org.springframework.ui.Model model) {

    Game game = gameService.getGameById(gameId);

    purchaseService.buyGame(getMockUser(), game, paymentMethod);

    model.addAttribute("game", game);
    model.addAttribute("paymentMethod", paymentMethod);

    return "purchase-success";
}
}