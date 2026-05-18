package com.steamunla.steamunla.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Game;
import com.steamunla.steamunla.model.Review;
import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.GameRepository;
import com.steamunla.steamunla.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    public Review addReview(User user, Game game, Review review) {

        review.setUser(user);
        review.setGame(game);
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        updateAverage(game);

        return saved;
    }

    /**
     * Obtiene todas las reseñas de un juego
     * @param game el juego
     * @return lista de reseñas del juego
     */
    public List<Review> getReviewsByGame(Game game) {
        return reviewRepository.findByGame(game);
    }

    private void updateAverage(Game game) {

        List<Review> reviews = reviewRepository.findByGame(game);

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        game.setAverageRating(avg);

        gameRepository.save(game);
    }
}