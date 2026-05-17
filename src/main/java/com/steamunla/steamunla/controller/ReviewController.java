package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Review;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.UserRepository;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    private User getMockUser() {
        return userRepository.findByUsername("matiA").orElseThrow();
    }

    @PostMapping("/{gameId}")
    public String addReview(@PathVariable Long gameId,
                            @ModelAttribute Review review) {

        Game game = gameService.getGameById(gameId);

        reviewService.addReview(getMockUser(), game, review);

        return "redirect:/games";
    }
}