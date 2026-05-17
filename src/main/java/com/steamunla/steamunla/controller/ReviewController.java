package com.steamunla.steamunla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Review;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.service.GameService;
import com.steamunla.steamunla.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;

    private User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute("loggedUser");
    }

    @PostMapping("/{gameId}")
    public String addReview(@PathVariable Long gameId,
                            @ModelAttribute Review review,
                            HttpSession session) {

        User user = getLoggedUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        reviewService.addReview(user, game, review);

        return "redirect:/games";
    }
}