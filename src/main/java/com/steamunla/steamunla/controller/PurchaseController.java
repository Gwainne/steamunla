package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/{gameId}")
    public String buyGame(@PathVariable Long gameId,
                          @RequestParam String paymentMethod) {

        Game game = gameService.getGameById(gameId);

        purchaseService.buyGame(getMockUser(), game, paymentMethod);

        return "redirect:/library";
    }
}